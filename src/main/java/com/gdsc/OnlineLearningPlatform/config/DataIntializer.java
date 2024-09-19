//package com.gdsc.OnlineLearningPlatform.config;
//
//import com.gdsc.OnlineLearningPlatform.model.Role;
//import com.gdsc.OnlineLearningPlatform.repository.RoleRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DataIntializer {
//
//    @Bean
//    CommandLineRunner init(RoleRepository roleRepository){
//
//        return args -> {
//            if(roleRepository.count() == 0){
//                roleRepository.save(new Role("STUDENT"));
//                roleRepository.save(new Role("INSTRUCTOR"));
//                roleRepository.save(new Role("ADMIN"));
//            }
//        };
//    }
//}
