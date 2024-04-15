package com.wiktormalyska.backend.hibernate;

import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses({
        ItemsTests.class
})
@SuiteDisplayName("Hibernate tests")
public class HibernateTests {
}
