package com.gsil.gradleptos.signup.service;

import com.gsil.gradleptos.Constants;
import com.gsil.gradleptos.DbObject;
import com.gsil.gradleptos.ErrorCode;
import com.gsil.gradleptos.EventResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpServiceImpl {

    private final DbObject db;
    private final ErrorCode err;
    private final Constants cons;

    public EventResult CheckUserLogin(int platformType, String platformKey, String authId, String authToken) {
        EventResult eventResult = new EventResult();

        //일반 유저 권한 인지 확인
        eventResult = CheckUserAuthData(authId, authToken, Constants.USER_TYPE_USER);
        if (eventResult.getRsCode() == 0)
        {
            long userId = (long) eventResult.getRsObj().get("user_id");

            // 유저 권한의 로그인
            eventResult = GetUserLoginAuth(userId, platformType, platformKey, authId, authToken);
        }
        else
        {
            // 관리자 권한의 로그인
            eventResult = CheckUserAuthData(authId, authToken, Constants.USER_TYPE_ADMIN);

            if (eventResult.getRsCode() == 0)
            {
                System.out.println("\n\n\n eventResult : "+ eventResult.getRsObj() +"\n\n\n");
                long userId = (long) eventResult.getRsObj().get("user_id");
                eventResult = GetAdminLoginAuth(userId, platformType, platformKey, authId, authToken);
            }
        }

        return eventResult;
    }

    public EventResult CheckUserAuthData(String authId, String authToken, String userType){

        EventResult eventResult = new EventResult();

        String sql = """
                        SELECT	IFNULL(
                        (   SELECT	user_id
                            FROM	USER_INFO
                            WHERE	1 = 1
                            AND	    auth_id = :auth_id
                            AND	    auth_token = SHA2(:auth_token, 512)
                            AND	    user_type = :user_type
                            AND	    delete_state = FALSE
                        ), 0 ) AS user_id;
                        """;

        Map<String,Object> param = new HashMap<>();

        param.put("auth_id",authId);
        param.put("auth_token",authToken);
        param.put("user_type",userType);

        eventResult = db.SelectByMap("CheckUserAuthData", sql, param);

        if ((long) eventResult.getRsMap().get(0).get("user_id") > 0) {
            eventResult = err.E0("CheckUserAuthData", null, null, eventResult.getRsMap().get(0));
        }else {
            eventResult = err.E1008("CheckUserAuthData", null, null, eventResult.getRsMap().get(0));
        }
        return eventResult;
    }

    public EventResult GetUserLoginAuth(long userId, int platformType, String platformKey, String auth_id, String auth_token)
    {
        EventResult eventResult = new EventResult();

        Map<String, Object> login_value = new HashMap<String, Object>();
        // 접속 플랫폼 확인
        eventResult = CheckUserPlatform(userId, platformType, platformKey);

        if (eventResult.getRsCode() == 0)
        {
            // 세션명 가져오기
            String user_session = eventResult.getRsObj().get("user_session").toString();

            // 기본 정보 가져오기 유저 업체 파일 권한
            eventResult = GetUserBasicData(userId);

            if (eventResult.getRsCode() == 0)
            {
                eventResult.putRsObj("user_session", user_session);

                login_value.put("user_data", eventResult.getRsObj());

                // 로그인 상태 변경
                UpdateUserLoginState(userId, platformType, user_session, true);

                eventResult = err.E0("Login Complete", null, null, login_value);
            }
        }

        return eventResult;
    }
    public EventResult GetAdminLoginAuth(long userId, int platformType, String platformKey, String authId, String authToken) {
        EventResult eventResult = new EventResult();

        Map<String, Object> login_value = new HashMap<String, Object>();
        // 플랫폼 체크
        eventResult = CheckUserPlatform(userId, platformType, platformKey);


        if (eventResult.getRsCode() == 0) {
            String user_session = eventResult.getRsObj().get("user_session").toString();

            // 기본정보 가져오기
            eventResult = GetUserBasicData(userId);

            if (eventResult.getRsCode() == 0) {
                eventResult.putRsObj("user_session", user_session);

                login_value.put("user_data", eventResult.getRsObj());

                eventResult = CheckUserPermissionArea(userId);

                // 권한체크
                if (eventResult.getRsCode() == 0)
                {
                    Integer permission_level = (Integer) eventResult.getRsObj().get("permission_level");

//                    eventResult = GetAdminMenu(permission_level);

                    // 메뉴체크
                    if (eventResult.getRsCode() == 0)
                    {
                        login_value.put("admin_menu", null);
                        // 로그인 상태 변경
                        UpdateUserLoginState(userId, platformType, user_session, true);

                        eventResult = err.E0("Login Complete", null, null, login_value);
                    }
                }
            }
        }

        return eventResult;
    }

    public EventResult CheckUserPlatform(long userId, int platformType, String platformKey)
    {
        EventResult eventResult = new EventResult();

        String sql = """
                SELECT	COUNT(1) AS row_count
                FROM	USER_PLATFORM
                WHERE	user_id = :user_id
                AND		platform_type = :platform_type ;
                """	;

        Map<String,Object> param = new HashMap<>();

        param.put("user_id",userId);
        param.put("platform_type",platformType);

        long rowCount = db.SelectByMapForCountNum("Check_UserPlatform_Conn", sql, param);

        String user_session = cons.Random_Str(16);
        // 플랫폼 정보 없으면 insert 있으면 update
        if (rowCount == 0)
        {
            eventResult = InsertUserPlatform(userId, platformType, platformKey, user_session);
        }
        else
        {
            eventResult = UpdateUserPlatform(userId, platformType, platformKey, user_session);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_session", user_session);

        eventResult.setRsObj(map);

        return eventResult;
    }

    private EventResult UpdateUserPlatform(long userId, int platformType, String platformKey, String userSession) {
        EventResult eventResult = new EventResult();

        String sql ="""		
                    UPDATE	USER_PLATFORM
                	SET		user_session = :user_session,
                			update_date = NOW()
                	WHERE	user_id = :user_id
                	AND		platform_type = :platform_type
                """;
        
        // device 까지 구분시 platformKey조건을 포함한다.

        Map<String, Object> setParam = new HashMap<String, Object>();

        setParam.put("user_id", userId);
        setParam.put("platform_type", platformType);
        //setParam.put("platform_key", platform_key);
        setParam.put("user_session", userSession);

        eventResult = db.InsertByMap("Update_UserPlatform", sql, setParam);

        return eventResult;
    }

    public EventResult GetUserBasicData(Long userId) {
        EventResult eventResult = new EventResult();

        Map<String, Object> map = new HashMap<>();

        map.put("user_data", GetUserData(userId).getRsObj());

        try {
            // 업체 정보 가져오기
            map.put("user_main_company", GetUserMainCompanyData(userId).getRsMap().get(0));
        } catch (Exception e) {
            map.put("user_main_company", Collections.emptyList());
        }
        //파일 정보
        map.put("user_file", GetUserFile(userId).getRsMap());
        // 권한 정보
        map.put("user_permission", GetUserMainPermission(userId).getRsObj());

        eventResult = err.E0("Get_UserBasicData", null, null, map);

        return eventResult;
    }

    private EventResult GetUserMainPermission(Long userId) {
        EventResult eventResult = new EventResult();

        String sql = """
                SELECT	user_id,
                        permission_level,
                        scene_id,
                        ctgo_duty_id,
                        (   SELECT	scene_name
                            FROM	SCENE_INFO
                            WHERE	scene_id = USER_PERMISSION.scene_id) AS scene_name,
                            IFNULL((    SELECT	ctgo_duty_name
                                        FROM	CTGO_DUTY
                                        WHERE	ctgo_duty_id = USER_PERMISSION.ctgo_duty_id
                                        AND		delete_state = FALSE
                                    ), '(알수없음)'
                        ) AS ctgo_duty_name
                FROM	USER_PERMISSION
                WHERE	user_id = :user_id
                AND		main_state = TRUE
                LIMIT 1
                """;

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);

        eventResult = db.SelectByMap("Get_UserMainPermission_Conn", sql, map);

        if (eventResult.getRsMap().size() == 0) {
            return eventResult = err.E0("Get_UserMainPermission_Conn", "No user File", null, null);
        }

        Map<String, Object> resultObj = eventResult.getRsMap().get(0);
        eventResult.setRsObj(resultObj);
        eventResult.setRsMap(null);

        return eventResult;
    }

    private EventResult GetUserFile(Long userId) {
        EventResult eventResult = new EventResult();

        String sql = """
                SELECT	user_id, seq_no,
                                file_url, file_name, file_type, file_size,
                                content_type, order_no, view_type,
                        CONCAT(file_url, file_name) AS file_url_full
                        FROM	USER_FILE
                        WHERE	user_id = ?
                        AND		delete_state = FALSE
                        ORDER BY seq_no DESC;
                """;

        List<Object> setParam = new ArrayList<Object>();

        setParam.add(userId);

        eventResult = db.SelectByList("Get_UserFile_Conn", sql, setParam);

        return eventResult;
    }

    private EventResult GetUserMainCompanyData(Long userId) {
        EventResult eventResult = new EventResult();

        String sql = """
                	SELECT  USER_COMPANY.user_id,
                	        USER_COMPANY.scene_id,
                	        (   SELECT  scene_name
                	            FROM    SCENE_INFO
                	            WHERE   SCENE_INFO.scene_id = USER_COMPANY.scene_id
                            ) AS scene_name,
                            USER_COMPANY.company_id,
                            USER_COMPANY.ctgo_construction_id,
                            USER_COMPANY.ctgo_occupation_id,
                            USER_COMPANY.company_position,
                            (   SELECT  user_type
                                FROM    USER_INFO
                                WHERE   user_id = :user_id
                            ) AS user_type,
                            COMPANY_INFO.company_name,
                            COMPANY_INFO.company_type,
                            (   SELECT	ctgo_construction_name
                                FROM	CTGO_CONSTRUCTION
                                WHERE	ctgo_construction_id = USER_COMPANY.ctgo_construction_id
                           ) AS ctgo_construction_name,
                           (    SELECT	ctgo_occupation_name
                                FROM	CTGO_OCCUPATION
                                WHERE	ctgo_occupation_id = USER_COMPANY.ctgo_occupation_id
                           ) AS ctgo_occupation_name
                    FROM	USER_COMPANY
                    JOIN	COMPANY_INFO    ON	(   COMPANY_INFO.company_id = USER_COMPANY.company_id
                                            AND     COMPANY_INFO.delete_state = FALSE
                                                )
                    WHERE	USER_COMPANY.user_id = :user_id
                    AND		USER_COMPANY.main_state = TRUE
                    AND		USER_COMPANY.delete_state = FALSE
                """;

        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);

        eventResult = db.SelectByMap("GetUserMainCompanyData_Conn", sql, param);

        return eventResult;
    }

    private EventResult GetUserData(Long userId) {
        EventResult eventResult = new EventResult();

        String sql = """	
                   SELECT	user_id, 
                            user_type,  
                            auth_id, 
                			user_name, 
                			user_phone, 
                			user_email, 
                			user_birthday,
                        	user_address, 
                        	user_address_detail, 
                        	ctgo_nation_id, 
                        	user_foreigner_no, 
                        	(   SELECT	ctgo_nation_name
                			    FROM	CTGO_NATION
                        	    WHERE	ctgo_nation_id = USER_INFO.ctgo_nation_id
                        	) AS ctgo_nation_name,
                			(   SELECT	COUNT(1)
                			    FROM	WORK_REQUEST_LOG
                        	    WHERE	user_id = USER_INFO.user_id
                			    AND		work_date = DATE(NOW())
                			    AND		scene_id = (    SELECT	scene_id
                								        FROM	USER_COMPANY
                								        WHERE	USER_COMPANY.user_id = USER_INFO.user_id
                								        AND		USER_COMPANY.main_state = TRUE
                								        LIMIT	1
                                                    )
                            ) AS work_permission
                    FROM	USER_INFO
                	WHERE	user_id = :user_id ;
                """;

        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);


        eventResult = db.SelectByMap("Get_UserData_Conn", sql, param);

        return eventResult;
    }

    @Transactional
    public EventResult InsertUserPlatform(long userId, Integer platformType, String platformKey,
                                           String user_session)
    {
        EventResult eventResult = new EventResult();

        String sql = """		
                    INSERT INTO	
                    USER_PLATFORM   (   user_id,   platform_type,  platform_key,   user_session)
                	VALUES		    (   :user_id,  :platform_type, :platform_key,  :user_session)
                	""";

        Map<String, Object> setParam = new HashMap<String, Object>();

        setParam.put("user_id", userId);
        setParam.put("platform_type", platformType);
        setParam.put("platform_key", platformKey);
        setParam.put("user_session", user_session);

        eventResult = db.InsertByMap("Insert_UserPlatform", sql, setParam);

        return eventResult;
    }
    @Transactional
    public EventResult UpdateUserLoginState(long userId, int platformType, String userSession,
                                             Boolean loginState)
    {
        EventResult eventResult = new EventResult();

        String sql = """
                    UPDATE	USER_PLATFORM
                    SET	    login_state = :login_state,
                    	    update_date = NOW()
                    WHERE   user_id = :user_id
                    AND	    platform_type = :platform_type
                    """;

        Map<String, Object> param = new HashMap<>();

        param.put("login_state", loginState);
        param.put("user_id", userId);
        param.put("platform_type", platformType);
        
        return db.InsertByMap("UpdateUserLoginState", sql, param);
    }

    public EventResult CheckUserPermissionArea(long userId)
    {
        EventResult eventResult = new EventResult();

        String sql = """
                SELECT    user_id,
                        permission_level,
                        scene_id,
                        (   SELECT	company_id
                            FROM	USER_COMPANY
                            WHERE	user_id = :user_id
                            AND		main_state = TRUE
                            LIMIT	1) AS company_id,
                        (   SELECT	company_type
                            FROM	COMPANY_INFO
                            WHERE	company_id = (
                                SELECT	company_id
                                FROM	USER_COMPANY
                                WHERE	user_id = :user_id
                                AND	    main_state = TRUE
                                LIMIT	1
                            )
                        ) AS company_type
                FROM    USER_PERMISSION
                WHERE	1=1
                AND     (   user_id = :user_id
                        AND main_state = TRUE
                        AND scene_id IN (   SELECT	scene_id
                                            FROM	COMPANY_SCENE
                                            WHERE	company_id = (
                                                SELECT	company_id
                                                FROM	USER_COMPANY
                                                WHERE	user_id = :user_id
                                                AND	    main_state = TRUE   LIMIT	1
                                            )
                                        )
                        )
                OR	    (
                            user_id = :user_id
                        AND scene_id = 0
                        )
                LIMIT	1;
                """;

        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        eventResult = db.SelectByMap("Check_UserPermissionArea_Conn", sql, param);

        if (eventResult.getRsCode() != 0) {
            eventResult = err.E4004("Check_UserPermissionArea", null, null, null);
            return eventResult;
        }

        int permission_level = Integer.valueOf(String.valueOf(eventResult.getRsMap().get(0).get("permission_level")));
        int scene_id = Integer.valueOf(String.valueOf(eventResult.getRsMap().get(0).get("scene_id")));
        int company_id = Integer.valueOf(String.valueOf(eventResult.getRsMap().get(0).get("company_id")));
        String company_type = String.valueOf(eventResult.getRsMap().get(0).get("company_type"));

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("permission_level", permission_level);
        map.put("scene_id", scene_id);
        map.put("company_id", company_id);
        map.put("company_type", company_type);

        eventResult = err.E0("Check_UserPermissionArea", null, null, map);

        return eventResult;
    }

    public EventResult Get_AdminMenu(int permission_level) {
        EventResult eventResult = new EventResult();

        try {
            String level_json = "";

            switch (permission_level) {
                case 0:
                    level_json = "RootMaster.json";
                    break;
                case 1:
                    level_json = "SceneMaster.json";
                    break;
                case 2:
                    level_json = "GeneralMaster.json";
                    break;
                case 3:
                    level_json = "ViewerMaster.json";
                    break;
            }

            ClassPathResource resource = new ClassPathResource("/menu/"+level_json);

            Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8");

            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("data", new Gson().fromJson(reader, com.gsil.menu.Main.class));

            eventResult = err.E0("Get_AdminMenu", null, null, map);
        }
        catch (Exception e)
        {e.printStackTrace();
            eventResult = err.E1001("Get_AdminMenu", e.toString(), null, eventResult.getRsObj());
        }

        return eventResult;
    }

}
