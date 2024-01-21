package com.distribute.persistence.aspect.filter;

import com.distribute.persistence.util.ThreadMdcUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            init((HttpServletRequest) request);
            chain.doFilter(request, response);
        } finally {
            MDC.remove("traceId");
        }
    }

    private void init(HttpServletRequest request) {
        String traceId = request.getParameter("traceId");
        traceId = StringUtils.isBlank(traceId) ? ThreadMdcUtil.getTraceId() : traceId;
        MDC.put("traceId", traceId);
    }
}
