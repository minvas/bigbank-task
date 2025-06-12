package org.minvas.dragonsofmugloar;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.minvas.dragonsofmugloar.game.GameRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DragonsOfMugloarApplicationTests {

    @MockitoBean
    private GameRunner gameRunner;

    @Test
    void Context_loads() {
    }

}
