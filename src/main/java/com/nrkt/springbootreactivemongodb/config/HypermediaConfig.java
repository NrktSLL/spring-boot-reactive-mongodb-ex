package com.nrkt.springbootreactivemongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@EnableHypermediaSupport(type = {
        EnableHypermediaSupport.HypermediaType.HAL,
        EnableHypermediaSupport.HypermediaType.HAL_FORMS})
@Configuration
public class HypermediaConfig { //For Webflux
}

