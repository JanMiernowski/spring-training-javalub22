package pl.sda.springtrainingjavalub22.external.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import pl.sda.springtrainingjavalub22.domain.user.User;

public class DatabseUserRepositoryTest {

    private ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);

    private JpaUserRepository userRepository = Mockito.mock(JpaUserRepository.class);
    private DatabaseUserRepository sut = new DatabaseUserRepository(userRepository);

    @Test
    public void shouldPersistUserInRepository() {
        //given
        User user = new User("login", "pass", "admin", true);
        //when
        sut.create(user);
        //then
        Mockito.verify(userRepository).save(userCaptor.capture());

        UserEntity entity = userCaptor.getValue();

        Assertions.assertEquals("login", entity.getUsername());
        Assertions.assertEquals("pass", entity.getPassword());
        Assertions.assertEquals("admin", entity.getRole());
    }
}
