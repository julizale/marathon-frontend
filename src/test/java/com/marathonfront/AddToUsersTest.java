package com.marathonfront;

import com.marathonfront.domain.User;
import com.marathonfront.domain.enumerated.Sex;
import com.marathonfront.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class AddToUsersTest {

//    private UserService userService = UserService.getInstance();
//
//    @Test
//    void addSampleUsersToDb() {
//
//        Random generator = new Random();
//
//        List<String> firstnames = List.of("Stefan", "Natalia", "Mirosław", "John", "Wiera", "Anna",
//                "Andrij", "Teresa", "Roman", "Krzysztof", "Igor", "Julia", "Magda", "Ryszard", "Agata");
//        List<String> lastnames = List.of("Kowalczyk", "Smith", "Rzeniszuk", "Wojtczak", "Romanienko", "Trzepizur",
//                "Komendant", "Chudyba", "Ciuk", "Waltz", "Mogiła", "Korwin", "Lis", "Wilk", "Polak");
//        List<String> cities = List.of("Częstochowa", "Warszawa", "Częstochowa", "Kraków", "Częstochowa", "Blachownia",
//                "Częstochowa", "Kłobuck", "Częstochowa", "Gdańsk", "Częstochowa", "Wrocław", "Częstochowa", "Poznań", "Częstochowa");
//
//        for (int i = 0; i < 100; i++) {
//            int rand1 = generator.nextInt(15);
//            int rand2 = generator.nextInt(15);
//            int rand3 = generator.nextInt(15);
//            int day = generator.nextInt(1, 29);
//            int month = generator.nextInt(1, 13);
//            int year = generator.nextInt(1953, 2005);
//            String firstname = firstnames.get(rand1);
//            String lastname = lastnames.get(rand2);
//            String email = firstname.toLowerCase() + "." + lastname.toLowerCase() + i +
//                    "@" + "domain.pl";
//            User user = new User();
//            user.setFirstName(firstname);
//            user.setLastName(lastname);
//            user.setCity(cities.get(rand3));
//            user.setEmail(email);
//            user.setPassword("password" + i);
//            user.setSex(generator.nextBoolean() ? Sex.MALE : Sex.FEMALE);
//            user.setBirthDate(LocalDate.of(year, month, day));
//
//            userService.saveUser(user);
//        }
//    }
}
