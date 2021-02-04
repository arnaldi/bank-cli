package co.arnaldi;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class BaseTestMockCase {

    @BeforeEach
    void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
}
