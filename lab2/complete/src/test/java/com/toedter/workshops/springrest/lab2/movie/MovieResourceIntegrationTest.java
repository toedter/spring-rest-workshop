package com.toedter.workshops.springrest.lab2.movie;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class MovieResourceIntegrationTest {

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected LinkDiscoverers links;

    protected MockMvc mvc;

    @BeforeEach
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context).
                addFilter(new ShallowEtagHeaderFilter()).
                build();
    }

    @Test
    public void shouldGetMovies() throws Exception {
        MockHttpServletResponse response2 = mvc.perform(get("/api/movies")).
                andDo(MockMvcResultHandlers.print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(HAL_JSON_VALUE)).
                andExpect(jsonPath("_embedded.movies", CoreMatchers.notNullValue())).
                andReturn().
                getResponse();

        LinkDiscoverer discoverer = links.getLinkDiscovererFor(response2.getContentType()).get();
        Link link = discoverer.findLinkWithRel("self", response2.getContentAsString()).get();
        String href = link.getHref();

        mvc.perform(get(href)).
                andDo(MockMvcResultHandlers.print()).
                andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON)).
                andExpect(jsonPath("_embedded.movies", CoreMatchers.notNullValue()));
    }
}
