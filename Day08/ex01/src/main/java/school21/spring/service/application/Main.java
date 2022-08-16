package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("userRepositoryJdbc", UsersRepository.class);
        System.out.println(usersRepository.findAll().get(0));
        usersRepository = context.getBean("userRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findAll().get(0));
    }
}
