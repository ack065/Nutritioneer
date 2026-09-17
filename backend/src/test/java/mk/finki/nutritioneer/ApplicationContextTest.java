package mk.finki.nutritioneer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Smoke test: the context loads, which means every bean, repository query and
 * security rule is at least wirable. Needs a running database, because the
 * repositories validate their native queries against it.
 */
@SpringBootTest
class ApplicationContextTest {

    @Test
    void contextLoads() {
    }
}
