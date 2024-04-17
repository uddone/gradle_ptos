package com.gsil.gradleptos.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LoggerFilter extends Filter<ILoggingEvent>
{

	@Override
	public FilterReply decide(ILoggingEvent event)
	{
		if (event.getMessage().contains("NOT_LOG_SQL"))
		{
			return FilterReply.DENY;
		}
		else return FilterReply.ACCEPT;
		
	}

}
