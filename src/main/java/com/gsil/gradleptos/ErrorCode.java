package com.gsil.gradleptos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ErrorCode
{
    public EventResult ECostom(String rqMethod, Integer rsCode, String rsMsg, String exMsg, List<Map<String, Object>> rsMap,
	    Map<String, Object> rsObj)
    {
	return Result(rqMethod, rsCode, rsMsg, exMsg, rsMap, rsObj);
    }

    // Complete
    /**
     * @apiNote None Error
     * @details 성공
     */
    public EventResult E0(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 0, "성공", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote None Error AND None ResultMsg
     */
    public EventResult E0(String rqMethod, String rsMsg, String exMsg, List<Map<String, Object>> rsMap,
	    Map<String, Object> rsObj)
    {
	return Result(rqMethod, 0, rsMsg, exMsg, rsMap, rsObj);
    }
    // Complete

    // 1000 
    /**
     * @apiNote 예외발생
     */
    public EventResult E1001(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1001, "예외발생", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 올바르지 않은 세션
     */
    public EventResult E1002(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1002, "올바르지 않은 세션", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 올바르지 않은 세션 (노무용)
     */
    public EventResult E1003(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1003, "올바르지 않은 세션 (노무)", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 데이터 등록 실패
     */
    public EventResult E1005(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1005, "데이터 등록 실패", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 데이터 수정 실패
     */
    public EventResult E1006(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1006, "데이터 수정 실패", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 데이터 삭제 실패
     */
    public EventResult E1007(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1007, "데이터 삭제 실패", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 데이터를 찾을 수 없습니다.
     */
    public EventResult E1008(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1008, "데이터를 찾을 수 없습니다", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 데이터가 없습니다. 0 Rows
     */
    public EventResult E1009(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1009, "데이터가 없습니다. 0 Rows", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote Not Match Data
     */
    public EventResult E1010(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1010, "Not Match Data", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote 권한이 없습니다.
     */
    public EventResult E1011(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1011, "권한이 없습니다.", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote 이미 데이터가 있습니다.
     */
    public EventResult E1012(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 1012, "이미 데이터가 있습니다.", exMsg, rsMap, rsObj);
    }
    // 1000 

    // 2000 
    /**
     * @apiNote Json 변환 실패
     */
    public EventResult E2001(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 2001, "Json 변환 실패", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 파일 등록 실패
     */
    public EventResult E2002(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 2002, "파일 등록 실패", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 제한 용량을 초과하였습니다.
     */
    public EventResult E2005(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 2005, "제한 용량을 초과하였습니다.", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 지원되지 않는 확장자 입니다.
     */
    public EventResult E2006(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 2005, "지원되지 않는 확장자 입니다.", exMsg, rsMap, rsObj);
    }
    // 2000 

    // 3000 
    /**
     * @apiNote 중복된 아이디입니다.
     */
    public EventResult E3001(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 3001, "중복된 아이디입니다.", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 중복된 전화번호입니다.
     */
    public EventResult E3002(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 3002, "중복된 전화번호입니다.", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 메인 출역업체가 하나이상 선택되어야 합니다.
     */
    public EventResult E3010(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 3010, "메인 출역업체가 하나이상 선택되어야 합니다.", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote 노무 급여정보를 이미 등록하였습니다.
     */
    public EventResult E3018(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 3018, "노무 급여정보를 이미 등록하였습니다.", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote 해당 중장비의 작업을 종료하시겠습니다?
     */
    public EventResult E3019(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 3019, "해당 중장비의 작업을 종료하시겠습니까?", exMsg, rsMap, rsObj);
    }
    // 3000 

    // 4000 
    /**
     * @apiNote 아이디에 사용할 수 업는 문자가 포함되어있습니다.
     */
    public EventResult E4001(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 4001, "아이디에 사용할 수 업는 문자가 포함되어있습니다.", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 비밀번호에 사용할 수 업는 문자가 포함되어있습니다.
     */
    public EventResult E4002(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 4002, "비밀번호에 사용할 수 업는 문자가 포함되어있습니다.", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 전화번호 양식이 올바르지 않습니다.
     */
    public EventResult E4003(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 4003, "전화번호 양식이 올바르지 않습니다.", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote 권한이 종료된 관리자 입니다.
     */
    public EventResult E4004(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 4003, "권한이 종료된 관리자 입니다.", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote 비밀번호 양식이 틀렸습니다.
     */
    public EventResult E4005(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 4005, "비밀번호 양식이 틀렸습니다.", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote 인증번호가 올바르지 않거나, 유효시간이 경과하였습니다.
     */
    public EventResult E4006(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
	return Result(rqMethod, 4006, "인증번호가 올바르지 않거나, 유효시간이 경과하였습니다.", exMsg, rsMap, rsObj);
    }
    // 4000 
    
    /**
     * @apiNote 결재순서 대상자가 아닙니다.
     */
    public EventResult E5001(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
        return Result(rqMethod, 5001, "현재 결재순서 대상자가 아닙니다.", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote 회수가 불가능 합니다.
     */
    public EventResult E5002(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
        return Result(rqMethod, 5002, "회수 가능한 상태가 아닙니다.", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote 회수 시 차기 결재상태가 결재대기 상태가 아니라서 회수가 불가능 합니다.
     */
    public EventResult E5003(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
        return Result(rqMethod, 5003, "차기결재가 결재대기 상태가 아닙니다.", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote 상신자는 결재취소가 불가능 합니다.
     */
    public EventResult E5004(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
        return Result(rqMethod, 5003, "상신자는 결재취소가 불가능 합니다.", exMsg, rsMap, rsObj);
    }
    
    /**
     * @apiNote TBM TBM을 수행하지 않았습니다.
     */
    public EventResult E6001(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
        return Result(rqMethod, 6001, "TBM 수행 후 작업을 시작해주세요.", exMsg, rsMap, rsObj);
    }

    /**
     * @apiNote TBM 유효한 TBM이 아닙니다.
     */
    public EventResult E6002(String rqMethod, String exMsg, List<Map<String, Object>> rsMap, Map<String, Object> rsObj)
    {
        return Result(rqMethod, 6001, "유효한 TBM이 아닙니다.", exMsg, rsMap, rsObj);
    }
    
    
    
    public EventResult Result(String rqMethod, Integer rsCode, String rsMsg, String exMsg, List<Map<String, Object>> rsMap,
	    Map<String, Object> rsObj)
    {
	EventResult eventResult = new EventResult();

	eventResult.setRqMethod(rqMethod);
	eventResult.setRsCode(rsCode);
	eventResult.setRsMsg(rsMsg);
	eventResult.setExMsg(exMsg);
	eventResult.setRsMap(rsMap);
	eventResult.setRsObj(rsObj);

	return eventResult;
    }
}
