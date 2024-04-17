package com.gsil.gradleptos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j(topic = "Constants")
@Service
@RequiredArgsConstructor
public class Constants
{
	private static final ErrorCode err = new ErrorCode();

	public static final String ALL = "전체";

	public static final Integer AUTH_TYPE_PROJECT = 1;

	public static final String USER_TYPE_USER = "USER";
	public static final String USER_TYPE_ADMIN = "ADMIN";

	//APPROVAL_LINE table approval_state ENUM
	public static final String APPROVAL_STATE_RDY = "결재대기";
	public static final String APPROVAL_STATE_APY = "승인"; // 무음으로 푸시받기   
	public static final String APPROVAL_STATE_REJ = "반려";
	public static final String APPROVAL_STATE_CAN = "결재취소";
	public static final String APPROVAL_STATE_RETURN = "회수";

	//APPROVAL_INFO table current_approval_state ENUM
	public static final String CNT_APPROVAL_STATE_REJ = "반려";
	public static final String CNT_APPROVAL_STATE_STANDBY = "작성중";
	public static final String CNT_APPROVAL_STATE_REQ = "결재요청";
	public static final String CNT_APPROVAL_STATE_COM = "결재완료";
	public static final String CNT_APPROVAL_STATE_CNT = "결재중";

	public static final String NFC_TYPE_WORK = "작업"; // 작업공간
	public static final String NFC_TYPE_SCENE = "현장"; // 작업공간
	public static final String NFC_TYPE_HEAVY = "중장비"; // 작업공간

	public static final String STATE_REG = "등록"; // 무음으로 푸시받기
	public static final String STATE_STANDBY = "조치권고"; // 무음으로 푸시받기
	public static final String STATE_REQ = "권고접수"; // 무음으로 푸시받기
	public static final String STATE_COM = "조치완료"; // 무음으로 푸시받기

	public static final String COMPANY_TYPE_PARTNER = "협력사";
	public static final String COMPANY_TYPE_HEAD = "발주처";
	public static final String COMPANY_TYPE_CONTRACTOR = "시공사";
	public static final String COMPANY_TYPE_DIRECT = "직영";

	public static final Integer USER_LEVEL_ROOT_MASTER = 0;
	public static final Integer USER_LEVEL_SCENE_MASTER = 1;
	public static final Integer USER_LEVEL_GENERAL_MASTER = 2;
	public static final Integer USER_LEVEL_VIEWER_MASTER = 3;

	public static final String WORKSPACE_WORK = "작업";
	public static final String WORKSPACE_SCENE = "현장";
	public static final String WORKSPACE_HEAVY = "중장비";

	public static final String INSPECTION_COM = "점검완료"; // 무음으로 푸시받기
	public static final String INSPECTION_STANDBY = "미점검"; // 무음으로 푸시받기

	public static final String CHECK_STANDBY = "확인필요"; // 무음으로 푸시받기
    public static final String CHECK_COM = "확인됨"; // 무음으로 푸시받기
    
	public static final String PUSH_STATE_ON = "ON";
	public static final String PUSH_STATE_OFF = "OFF";
	public static final String PUSH_STATE_SILENT = "SILENT";

	public static final String GOOD = "양호"; // 무음으로 푸시받기
    public static final String BAD = "불량"; // 무음으로 푸시받기
    public static final String NONE = "해당없음"; // 무음으로 푸시받기
	
	public static final int APPROVAL_MODULE_RA = 1; //위험성평가 결재모듈
	public static final int APPROVAL_MODULE_WP = 2; //작업허가 결재모듈
	public static final int APPROVAL_MODULE_DR = 3; //공사일보 결재모듈
	public static final int APPROVAL_MODULE_EDU = 4; //교육 결재모듈
	public static final int APPROVAL_MODULE_TOOL = 5; // 공도구 결재모듈
	public static final int APPROVAL_MODULE_WORKPLAN = 6; // 작업계획서 결재모듈
	public static final int APPROVAL_MODULE_EQUIPINS = 7; // 중장비 점검 결재모듈
	public static final int APPROVAL_MODULE_SMC = 8; // 중장비 점검 결재모듈
	public static final int APPROVAL_MODULE_RAINS = 9; // 위평 점검 결재모듈
	public static final int APPROVAL_MODULE_RABP = 10; // 협력사 위평 결재모듈

	public static final String ALARM_TYPE_RISK_APPROVAL = "위험성평가결재";
	public static final String ALARM_TYPE_RISK_INSPECTION = "위험성평가일일점검";
	public static final String ALARM_TYPE_RISK_CHECKER = "위험성평가조치";
	public static final String ALARM_TYPE_RISK_BAD = "위험성평가부적합";
	public static final String ALARM_TYPE_EQUIP_BAD = "사용전장비부적합";
	public static final String ALARM_TYPE_INCONGRUITY = "부적합조치";
	public static final String ALARM_TYPE_SPOT_CHECKER = "취약개소점검";
	public static final String ALARM_TYPE_SPOT_BAD = "취약개소부적합";
	public static final String ALARM_TYPE_DAILYREPORT_APPROVAL = "공사일보결재";
	public static final String ALARM_TYPE_DAILYPLAN_APPROVAL = "작업허가서결재";
	public static final String ALARM_TYPE_CONTRACT = "계약서";
	public static final String ALARM_TYPE_EQUIP_BAD_QUESTION = "사용전장비불량";
	public static final String ALARM_TYPE_NOTICE = "공지사항";
	public static final String TOOL_TAG_ETC = "ETC"; // 기타
	public static final String TOOL_TAG_WOODWORKING = "WTL"; // 목공기계
	public static final String TOOL_TAG_EQUIPMENT = "EQP"; // 설비공구
	public static final String TOOL_TAG_WELDING = "WLM"; // 용접기
	public static final String TOOL_TAG_ELECTRONIC = "ELC"; // 전기용품
	public static final String TOOL_TAG_FIXTURES = "FXT"; // 조명기구
	public static final String TOOL_TAG_REINFORCING = "BWK"; // 철근가공
	public static final String TOOL_TAG_CONCRETE = "CON"; // 콘크리트
	/*
	* 	전기용품 : ELC-2023-00001
		목공기계 : WTL-2023-00001
		철근가공 : BWK-2023-00001
		콘크리트 : CON-2023-00001
		설비공구 : EQP-2023-00001
		용접기 : WLM-2023-00001
		조명기구 : FXT-2023-00001
		기타 : ETC-2023-00001
	* */
	public static final String[] protections = new String[]{"shoes", "mask", "breath", "glove", "clothes", "belt", "ear", "etc"};
	public static final String[] DAYS = new String[]{"월", "화", "수", "목", "금", "토", "일"};

	public static final String SERVICE_KEY = "k4NPvlmhbK%2BlIvln063LfA7oAMt6Ek2h709rzWS2PouvJEgXB3YBz83ldneQcXwn9jHo9IQ1Bm6JrrwiFkQkOA%3D%3D";
	public static final String ICON_PATH= "https://wpgumi.com/api/upload/weather/";
	/**
	 * @apiNote Aligo Param이 지정되어있습니다. 리시버와 메시지만 설정하면됩니다.
	 */
	public static final MultiValueMap<String, String> ALIGO_PARAM = new LinkedMultiValueMap<>() {
		{
			add("user_id", "admgsil");
			add("key", "8xzvf3098tjjrwiz4kjk180blui6xp1r");
			add("sender", "070-7574-1728");
			//			add("receiver", "");
			//			add("msg", "");
		}
	};

	/**
	 * @apiNote Aligo Param이 지정되어있습니다. 리시버와 메시지만 설정하면됩니다.
	 */
	public static final String ALIGO_URL = "https://apis.aligo.in/send/";

	/**
	 * @apiNote 구간 IN
	 */
	public static final String WORKSTATE_IN = "IN";

	/**
	 * @apiNote 구간 OUT
	 */
	public static final String WORKSTATE_OUT = "OUT";

	/**
	 * @apiNote 작업구간을 인식한 device - GPS
	 */
	public static final String WORKDEVICE_GPS = "GPS";

	/**
	 * @apiNote 작업구간을 인식한 device - NFC
	 */
	public static final String WORKDEVICE_NFC = "NFC";

	/**
	 * @apiNote Test Mode (상용화시 false)
	 */
	public static final Boolean TEST_MODE = true;

	public static final String REGIST_SECRET = "REGIST_SECRET";

	/**
	 * @apiNote 상대경로
	 */
	public static final String RELATIVE_PATH = "../src/main/java/pri/testgradle";

	public static final Map<String, String> IMAGE_EXT = new HashMap()
	{
		{
			put("jpg", ".jpg");
			put("jpeg", ".jpeg");
			put("png", ".png");
			put("gif", ".gif");
		}
	};

	public static final String NOW_MONTH = new SimpleDateFormat("yyyy-MM").format(new Date());
	public static final String NOW_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	public static final String NOW_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	public static final float IMAGE_QUALITY = 0.5f;

	public static final String DOWNLOAD_PATH = "https://wpgumi.com/file/download?path=";
	public static final String FILE_PATH = "/opt/image/"; //public
//	public static final String FILE_PATH = "./src/main/resources/static/";
	public static final String FILE_URL = "https://wpgumi.com/api/upload/"; //public
//	public static final String FILE_URL = "http://192.168.0.89:8085/";
//
	public static final String LINUX_FILE_URL = "file:///opt/image/";
	public static final Long FILE_LIMIT_SIZE = (long) 209715200; // 200mb

	/**
	 * @apiNote 회원의 프로필 이미지 파일 IMAGE
	 * 
	 */
	public static final String VIEW_TYPE_PROFILE = "profile";
	/**
	 * @apiNote 서명 파일 IMAGE
	 */
	public static final String VIEW_TYPE_SIGN = "sign";
	/**
	 * @apiNote 회원의 증명서 파일 IMAGE
	 */
	public static final String VIEW_TYPE_SAFE = "certificate";
	/**
	 * @apiNote 보여지지 않는 첨부파일 형식
	 */
	public static final String VIEW_TYPE_NONE = "none";
	/**
	 * @apiNote 미리보기 파일 형식 미리보기를 지원하는 contentType이어야합니다.
	 */
	public static final String VIEW_TYPE_PREVIEW = "preview";
	// Image

	// Regex
	public static final String REGEX_ID = "^[0-9a-zA-Z가-힣-_]+";
	public static final String REGEX_PASS = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
	public static final String REGEX_PHONE = "^\\d{2,3}\\d{3,4}\\d{4}$";
	public static final String REGEX_BIRTHDAY1 = "^(19\\d{2}|20\\d{2})-(0\\d|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])$";
	public static final String REGEX_BIRTHDAY2 = "^(19\\d{2}|20\\d{2}).(0\\d|1[0-2]).(0[1-9]|[1-2]\\d|3[0-1])$";
	public static final String REGEX_BIRTHDAY3 = "^(19\\d{2}|20\\d{2})(0\\d|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])$";
	public static boolean matchesBirthDay(String bithdayString) {
		return bithdayString.matches(REGEX_BIRTHDAY1) || bithdayString.matches(REGEX_BIRTHDAY2) || bithdayString.matches(REGEX_BIRTHDAY3);
	}

	public static final String licensePlateRegex1 = "^\\d{2,3}[가-힣]\\d{4}$";
	public static final String licensePlateRegex2 = "^[가-힣]{1,2}\\d{2}[가-힣]\\d{4}$";
	public static final String licensePlateRegex3 = "^[가-힣]{2}\\d{1}[가-힣]\\d{4}$";
	public static final String licensePlateRegex4 = "^(외교|영사|대표|국기|준외|준영|협정)\\d{6}$";
	public static final String licensePlateRegex5 = "^임\\d{4,6}$";
	public static final String licensePlateRegex6 = "^임시\\d{4,6}$";
	public static boolean validateLicensePlate(String licensePlate) {
		return licensePlate.matches(licensePlateRegex1)
				||licensePlate.matches(licensePlateRegex2)
				||licensePlate.matches(licensePlateRegex3)
				||licensePlate.matches(licensePlateRegex4)
				||licensePlate.matches(licensePlateRegex5)
				||licensePlate.matches(licensePlateRegex6);
	}

	// 세션 유지일
	public static final int SESSION_LIMIT_DATE = 90;

	// Level
	public enum PermissionLevel
	{
		Root, Scene, General
	}

	// Check ID Regex 
	public static boolean CheckPattern_ID(String authId)
	{
		if (authId == null || authId.length() <= 0)
		{
			return false;
		}

		return authId.matches(REGEX_ID);
	}

	// Check Phone Regex 
	public static boolean CheckPattern_Phone(String phoneNo)
	{
		if (phoneNo == null || phoneNo.length() <= 0)
		{
			return false;
		}

		return phoneNo.matches(REGEX_PHONE);
	}

	// Check Pass Regex

	public static boolean CheckPattern_Pass(String hr_password)
	{
		if (hr_password == null || hr_password.length() <= 0)
		{
			return false;
		}

		return hr_password.matches(REGEX_PASS);
	}

	
	/**
	 * @author Hansol
	 * @apiNote minNo, maxNo 사이의 값을 랜덤으로 뽑아냅니다.
	 */
	public static long Random_Int()
	{
		long minNo = 10000000L;
		long maxNo = 99999999L;

		long no = (long) (Math.random() * (maxNo - minNo + 1) + minNo);

		return no;
	}

	
	/**
	 * @author Hansol
	 * @param length
	 * @apiNote 배열값 중 랜덤으로 문자열을 봅아냅니다.
	 */
	public static String Random_Str(int length)
	{
		String randStr = "";

		String charsToUse = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		char[] charsArr = charsToUse.toCharArray();
		int arrCount = charsToUse.length() - 1;

		Random rand = new Random();

		for (int i = 0; i < length; i++)
		{
			randStr += charsArr[rand.nextInt(arrCount)];
		}

		return randStr;
	}
	
	/**
	 * @author Hansol
	 * @param length
	 * @apiNote 배열값 중 랜덤으로 숫자를 뽑아냅니다.
	 */
	public static String Random_Num(int length)
	{
		String randStr = "";

		String charsToUse = "1234567890";
		char[] charsArr = charsToUse.toCharArray();
		int arrCount = charsToUse.length() - 1;

		Random rand = new Random();

		for (int i = 0; i < length; i++)
		{
			randStr += charsArr[rand.nextInt(arrCount)];
		}

		return randStr;
	}

	/**
	 * @author Hansol
	 * @apiNote Post방식의 Http통신을 합니다. url과 param을 넣고 동작합니다.
	 * @param url
	 * @param param
	 */
	public EventResult Post_HttpConnetion(String url, MultiValueMap<String, String> param) {
		EventResult eventResult = new EventResult();

//		log.info("[START] HTTP_Connection ------------------>...");
//		System.out.println("[START] HTTP_Connection ------------------>...");

		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.add("content-Type", "multipart/form-data");

			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(param, headers);

			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

			Map<String, Object> map = new HashMap<>();

			map.put("response", response.getBody());

			eventResult = err.E0("Post_HttpConnetion", null, null, map);
//			log.info("[END] HTTP_Connection_ResPonse : " + response.getBody());
//			System.out.println("[END] HTTP_Connection_ResPonse : " + response.getBody());
		}
		catch (Exception e)
		{
			eventResult = err.E1001("Post_HttpConnetion", null, null, null);

//			log.info("[END] HTTP_Connection_ResPonse : " + e);
//			System.out.println("[END] HTTP_Connection_ResPonse : " + e);
		}
		finally
		{
//			log.info("[FINISH] HTTP_Connection" );
			System.out.println("[FINISH] HTTP_Connection");
		}

		return eventResult;
	}
}
