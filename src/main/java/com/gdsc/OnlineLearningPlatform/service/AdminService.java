package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.AdminDto;
import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.dto.CourseSubmissionDto;
import com.gdsc.OnlineLearningPlatform.dto.UserDto;
import com.gdsc.OnlineLearningPlatform.enums.CourseStatus;
import com.gdsc.OnlineLearningPlatform.mapper.CourseMapper;
import com.gdsc.OnlineLearningPlatform.mapper.CourseSubmissionMapper;
import com.gdsc.OnlineLearningPlatform.mapper.UserMapper;
import com.gdsc.OnlineLearningPlatform.model.*;
import com.gdsc.OnlineLearningPlatform.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final CourseSubmissionRepository courseSubmissionRepository;

    private final CourseRepository courseRepository;

    private final UserMapper userMapper;

    private final CourseMapper courseMapper;

    private final CourseSubmissionMapper courseSubmissionMapper;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository
                        , CourseSubmissionRepository courseSubmissionRepository
                        , CourseRepository courseRepository, UserMapper userMapper
                        , CourseMapper courseMapper, CourseSubmissionMapper courseSubmissionMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.courseSubmissionRepository = courseSubmissionRepository;
        this.courseRepository = courseRepository;
        this.userMapper = userMapper;
        this.courseMapper = courseMapper;
        this.courseSubmissionMapper = courseSubmissionMapper;
    }

    public ResponseEntity<String> addRoleToUser(Long userId, AdminDto adminDto) {

        Optional<User> optionalUser =  userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        Set<Role> userRoles = user.getRoles();

        for(String roleName: adminDto.getRolesNames()){
            Optional<Role> optionalRole = roleRepository.findByName(roleName.toUpperCase());
            if(optionalRole.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found: "+roleName);
            }

            Role role = optionalRole.get();
            if(userRoles.contains(role)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already have this role: "+roleName);
            } else {
                userRoles.add(role);
            }
        }

        user.setRoles(userRoles);
        userRepository.save(user);

        return ResponseEntity.ok("Roles added to user successfully");
    }

    public ResponseEntity<String> removeRoleFromUser(Long userId, AdminDto adminDto) {

        Optional<User> optionalUser =  userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        Set<Role> userRoles = user.getRoles();

        for(String roleName: adminDto.getRolesNames()){
            Optional<Role> optionalRole = roleRepository.findByName(roleName.toUpperCase());
            if(optionalRole.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found: "+roleName);
            }

            Role role = optionalRole.get();
            if(userRoles.contains(role)) {
                userRoles.remove(role);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have the role: "+roleName);
            }
        }

        user.setRoles(userRoles);
        userRepository.save(user);
        return ResponseEntity.ok("Role removed successfully");
    }

    public ResponseEntity<String> approveCourse(Long submissionId) {

        Optional<CourseSubmission> optionalCourseSubmission = courseSubmissionRepository.findById(submissionId);
        if(optionalCourseSubmission.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Submission not found");
        }

        CourseSubmission courseSubmission = optionalCourseSubmission.get();

        if (courseSubmission.getStatus() == CourseStatus.APPROVED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course submission has already been approved.");
        }
        if (courseSubmission.getStatus() == CourseStatus.REJECTED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course submission has already been rejected, you cannot approve it.");
        }

        courseSubmission.setStatus(CourseStatus.APPROVED);

        Course course = new Course();
        course.setTitle(courseSubmission.getTitle());
        course.setDescription(courseSubmission.getDescription());
        course.setInstructor(courseSubmission.getInstructor());
        course.setCategory(courseSubmission.getCategory());
        course.setTags(courseSubmission.getTags());
        courseSubmission.setCourse(course);

        courseSubmissionRepository.save(courseSubmission);

        return ResponseEntity.ok("Course submission approved and course created successfully");
    }

    public ResponseEntity<String> rejectCourse(Long submissionId) {

        Optional<CourseSubmission> optionalCourseSubmission = courseSubmissionRepository.findById(submissionId);
        if(optionalCourseSubmission.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Submission not found");
        }

        CourseSubmission courseSubmission = optionalCourseSubmission.get();

        if (courseSubmission.getStatus() == CourseStatus.APPROVED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course submission has already been approved, you cannot reject it.");
        }
        if (courseSubmission.getStatus() == CourseStatus.REJECTED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course submission has already been rejected");
        }

        courseSubmission.setStatus(CourseStatus.REJECTED);
        courseSubmission.setFeedback("Your Course "+courseSubmission.getTitle()+" has been rejected.");
        courseSubmission.setTitle("Rejected "+courseSubmission.getTitle());
        courseSubmissionRepository.save(courseSubmission);

        return ResponseEntity.ok("Course submission rejected");
    }

    public ResponseEntity<List<CourseSubmissionDto>> getAllSubmissions() {
        List<CourseSubmission> courseSubmissions = courseSubmissionRepository.findAll();
        List<CourseSubmissionDto> allCourseSubmissions =
                courseSubmissions.stream()
                        .map(courseSubmissionMapper::toCourseSubmissionDto)
                        .toList();

        return ResponseEntity.ok().body(allCourseSubmissions);
    }

    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        if(courses.isEmpty()){
            return ResponseEntity.ok().body("There are no courses");
        }

        List<CourseDto> allCourses = courses.stream()
                .map(courseMapper::toCourseDto).toList();

        return ResponseEntity.ok().body(allCourses);
    }

    public ResponseEntity<?> getCourseById(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Course course = optionalCourse.get();

        return ResponseEntity.ok(courseMapper.toCourseDto(course));
    }

    public ResponseEntity<?> getCourseByTitle(String title) {
        Optional<Course> optionalCourse = courseRepository.findByTitle(title);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Course course = optionalCourse.get();

        return ResponseEntity.ok(courseMapper.toCourseDto(course));
    }

    public ResponseEntity<String> deleteCourseById(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Course course = optionalCourse.get();

        courseRepository.delete(course);
        return ResponseEntity.ok("Course deleted Successfully");
    }

    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return ResponseEntity.ok().body("There are no users.");
        }

        List<UserDto> allUsers = users.stream().map(userMapper::toUserDto).toList();

        return ResponseEntity.ok().body(allUsers);
    }

    public ResponseEntity<?> getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = optionalUser.get();

        return ResponseEntity.ok().body(userMapper.toUserDto(user));
    }

    public ResponseEntity<String> deleteUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = optionalUser.get();

        userRepository.delete(user);
        return ResponseEntity.ok("User deleted Successfully");
    }
}
