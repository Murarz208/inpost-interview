package com.maciejmurawski.inpost;

import com.maciejmurawski.inpost.policies.discount.DiscountPolicyTestFactory;
import com.maciejmurawski.inpost.products.ProductsTestFactory;
import de.cronn.testutils.h2.H2Util;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(H2Util.class)
@ActiveProfiles(profiles = "test")
public class AbstractTest {

    @Autowired
    protected ProductsTestFactory productsFactory;
    @Autowired
    protected DiscountPolicyTestFactory discountPolicyFactory;
    @Autowired
    private H2Util h2Util;

    @BeforeEach
    void cleanupDatabase() {
        h2Util.resetDatabase();
    }

}
