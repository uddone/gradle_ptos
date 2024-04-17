package com.gsil.gradleptos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "DbObject")
@Service
@RequiredArgsConstructor
public class DbObject
{
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final ErrorCode err;


	public EventResult SelectByMap(String rqMethod, String sql, Map<String, Object> setParam)
	{
		EventResult eventResult = new EventResult();

		try
		{
			Query query = em.createNativeQuery(sql);

			for (Map.Entry<String, Object> params : setParam.entrySet())
			{
				if ((params.getValue() == null))
				{
					params.setValue("");
				}

				query.setParameter(params.getKey(), params.getValue());

			}

			query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			List<Map<String, Object>> list = query.getResultList();

			eventResult = err.E0(rqMethod, null, list, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Exception", e);
			eventResult = err.E1001(rqMethod, e.getMessage(), null, null);
		}

		return eventResult;
	}

	/**
	 * @author dukim
	 * @return Insert한 후 Insert한 Auto Pk값을 반환
	 */
	@Transactional
	public EventResult InsertByListForLastID(String rqMethod, String sql, List<Object> setParam) {
		EventResult eventResult = new EventResult();

		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					int i = 1;
					for (Object params : setParam) {
						statement.setObject(i, params);
						i++;
					}
					return statement;
				}
			}, keyHolder);

			Map<String, Object> mapObj = new HashMap<String, Object>();

			mapObj.put("last_id", keyHolder.getKey().longValue());

			eventResult = err.E0(rqMethod, null, null, mapObj);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception", e);
			eventResult = err.E1001(rqMethod, e.getMessage(), null, null);
		}
		return eventResult;
	}

	@Transactional
	public EventResult InsertByMap(String rqMethod, String sql, Map<String, Object> setParam) {
		EventResult eventResult = new EventResult();
		try {
			Query query = em.createNativeQuery(sql);

			for (Map.Entry<String, Object> params : setParam.entrySet()) {
				query.setParameter(params.getKey(), params.getValue());
			}

			query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			int resCount = query.executeUpdate();

			Map<String, Object> mapObj = new HashMap<String, Object>();

			mapObj.put("res_count", resCount);
			log.info("res_count : " + resCount);
//			System.out.println("res_count : "+resCount);
			eventResult = err.E0(rqMethod, null, null, mapObj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Exception", e);
			eventResult = err.E1001(rqMethod, e.toString(), null, null);
		}

		return eventResult;
	}

	@Transactional
	public EventResult InsertByList(String rqMethod, String sql, List<Object> setParam)
	{
		EventResult eventResult = new EventResult();

		try
		{
			Query query = em.createNativeQuery(sql);

			int i = 1;

			for (Object params : setParam)
			{
				query.setParameter(i, params);
				i++;
			}

			query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			int resCount = query.executeUpdate();

			if(resCount == 0)
			{
				return  err.E1005(rqMethod, null, null, null);
			}
			
			Map<String, Object> mapObj = new HashMap<String, Object>();

			mapObj.put("res_count", resCount);

			eventResult = err.E0(rqMethod, null, null, mapObj);
		}
		catch (Exception e)
		{
			log.error("Exception", e);
			eventResult = err.E1001(rqMethod, e.toString(), null, null);
		}

		return eventResult;
	}

	@Transactional
	public EventResult InsertSubList(String tableName, String pkCollName, Object pkValue, String targetCollName, String subList)
	{
		EventResult eventResult = new EventResult();

		try
		{
			eventResult = QueryDelete_To_SubList(tableName, pkCollName, pkValue);

			if (eventResult.getRsCode() != 0)
				return eventResult;

			JsonArray jArray = new Gson().fromJson(subList, JsonArray.class);

			if (jArray.size() > 0)
			{
				String subSql = "";

				for (JsonElement jElement : jArray)
				{
					subSql += "('" + pkValue + "', '" + jElement.getAsString() + "'),";
				}

				subSql = subSql.substring(0, subSql.length() - 1);

				String sql = "INSERT INTO " +
								tableName +
								" 		(" +
								pkCollName +
								", " +
								targetCollName +
								")" +
								" VALUES	" +
								subSql +
								";";

				eventResult = InsertByMap("QueryInsert_To_SubList_Table : " + tableName, sql,
						new HashMap<String, Object>());
			}
			else
			{				
				eventResult = err.E0("QueryInsert_To_SubList_Table : " + tableName, null, null, null);
			}
		}
		catch (Exception e)
		{
			log.error("Exception", e);
			eventResult = err.E1001("QueryInsert_To_SubList" + tableName, e.getMessage(), null, null);
		}

		return eventResult;
	}

	@Transactional
	public EventResult QueryDelete_To_SubList(String tableName, String pkCollName, Object pkValue)
	{
		EventResult eventResult = new EventResult();

		String sql = "DELETE FROM	" + tableName + " WHERE		" + pkCollName + " = " + pkValue + ";";

		eventResult = InsertByMap("QueryInsert_To_SubList_This", sql, new HashMap<String, Object>());

		return eventResult;
	}



	public EventResult SelectByList(String rqMethod, String sql, List<Object> setParam)
	{
		EventResult eventResult = new EventResult();

		try
		{
			Query query = em.createNativeQuery(sql);

			int i = 1;

			for (Object params : setParam)
			{
				query.setParameter(i, params);
				i++;
			}

			query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			List<Map<String, Object>> list = query.getResultList();

			eventResult = err.E0(rqMethod, null, list, null);
		}
		catch (EmptyResultDataAccessException e)
		{
			log.error("EmptyResultDataAccessException", e);
			eventResult = err.E1008(rqMethod, e.getMessage(), null, null);
		}
		catch (Exception e)
		{
			log.error("Exception", e);
			eventResult = err.E1001(rqMethod, e.getMessage(), null, null);
		}

		return eventResult;
	}


	public EventResult SelectByMapForObject(String rqMethod, String sql, Map<String, Object> setParam)
	{
		EventResult eventResult = new EventResult();

		try
		{
			Query query = em.createNativeQuery(sql);

			log.info("[debug]" + rqMethod + "[params]" + setParam.toString());

			for (Map.Entry<String, Object> params : setParam.entrySet()) {
				if ((params.getValue() == null))
				{
					params.setValue("");
				}

				log.info("[debug] param getKey = " + String.valueOf(params.getKey()));
				log.info("[debug] param getValue = " + params.getValue());

				query.setParameter(params.getKey(), params.getValue());
				log.info("[complete]");
			}

			query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			List<Map<String, Object>> list = query.getResultList();
			
			if(list != null && list.size() > 0)
			{
				eventResult = err.E0(rqMethod, null, null, list.get(0));
			}
			else {
				eventResult = err.E1008(rqMethod, null, null, null);
			}
		}
		catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			log.error("EmptyResultDataAccessException", e);
			eventResult = err.E1008(rqMethod, e.getMessage(), null, null);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception", e);
			eventResult = err.E1001(rqMethod, e.getMessage(), null, null);
		}

		return eventResult;
	}

	public EventResult SelectByMapForCount(String rqMethod, String sql, Map<String, Object> setParam)
	{
		EventResult eventResult = new EventResult();

		try
		{
			Query query = em.createNativeQuery(sql);

			log.info("[debug]" + rqMethod + "[params]" + setParam.toString());

			for (Map.Entry<String, Object> params : setParam.entrySet())
			{
				if ((params.getValue() == null))
				{
					params.setValue("");
				}

				log.info("[debug] param getKey = " + String.valueOf(params.getKey()));
				log.info("[debug] param getValue = " + params.getValue());

				query.setParameter(params.getKey(), params.getValue());
			}

			query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			List<Map<String, Object>> list = query.getResultList();
			Map<String, Object> map = new HashMap<>();

			map.put("row_count", list.get(0).get("row_count"));

			eventResult = err.E0(rqMethod, null, null, map);
		}
		catch (EmptyResultDataAccessException e)
		{
			log.error("EmptyResultDataAccessException", e);
			eventResult = err.E1008(rqMethod, e.getMessage(), null, null);
		}
		catch (Exception e)
		{
			log.error("Exception", e);
			eventResult = err.E1001(rqMethod, e.getMessage(), null, null);
		}

		return eventResult;
	}

	public long SelectByMapForCountNum(String rqMethod, String sql, Map<String, Object> setParam)
	{
		long row_count = 0;

		try
		{
			Query query = em.createNativeQuery(sql);

			log.info("[debug]" + rqMethod + "[params]" + setParam.toString());

			for (Map.Entry<String, Object> params : setParam.entrySet()) {
				if ((params.getValue() == null)) {
					params.setValue("");
				}

				log.info("[debug] param getKey = " + String.valueOf(params.getKey()));
				log.info("[debug] param getValue = " + params.getValue());
				query.setParameter(params.getKey(), params.getValue());
			}

			query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			List<Map<String, Object>> list = query.getResultList();

			String rowCon = list.get(0).get("row_count").toString();

			row_count = Long.parseLong(rowCon);

			if (row_count <= 0)
			{
				row_count = 0;
			}
		}
		catch (Exception e)
		{
			row_count = -1;
			log.error("Exception", e);
			e.printStackTrace();
		}

		return row_count;
	}

	public String SearchSql(String likeSql) {
		if ( likeSql == null || likeSql.isEmpty() ) {
			return "%%";
		} else {
			return "%" + likeSql + "%";
		}
	}
}
