package com.dianping.puma.config;

import javax.annotation.PostConstruct;

import com.dianping.puma.core.entity.PumaServer;
import com.dianping.puma.core.service.PumaServerService;
import com.dianping.puma.core.util.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pumaServerConfig")
public class PumaServerConfig implements InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(PumaServerConfig.class);

	@Autowired
	PumaServerService pumaServerService;

	private String id;

	private String name;

	private String host;

	private int port;

	@PostConstruct
	public void init() {
		for (String ip : IPUtils.getNoLoopbackIP4Addresses()) {
			PumaServer entity = pumaServerService.findByHost(ip);
			if (entity != null) {
				this.name = entity.getName();
				this.host = entity.getHost();
				this.port = entity.getPort();
				LOG.info("Initialize puma server: name `{}`, host `{}`, port `{}`.", new Object[] { name, host, port });
				break;
			}
		}

		if (this.name == null) {
			LOG.error("Initialize puma server error: No matched server found in DB.");
			throw new RuntimeException("Initialize puma server error: No matched server found in DB.");
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}