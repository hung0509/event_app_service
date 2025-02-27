package vn.nguyenanhtuan.eventapp.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.nguyenanhtuan.eventapp.constant.ErrorCode;
import vn.nguyenanhtuan.eventapp.dto.request.UserRequestDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;
import vn.nguyenanhtuan.eventapp.entity.Role;
import vn.nguyenanhtuan.eventapp.entity.User;
import vn.nguyenanhtuan.eventapp.handle.GlobalException;
import vn.nguyenanhtuan.eventapp.mapper.UserMapper;
import vn.nguyenanhtuan.eventapp.reposiroty.RoleRepository;
import vn.nguyenanhtuan.eventapp.reposiroty.UserRepository;
import vn.nguyenanhtuan.eventapp.service.UserService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponseDto save(UserRequestDto req) {
        if(userRepository.existsByEmail(req.getEmail()))
            throw new GlobalException(ErrorCode.USER_ALREADY_EXIST);

        User user = userMapper.toUser(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        Role role = roleRepository.findById(req.getRole()).orElseThrow(() -> new GlobalException(ErrorCode.ROLE_NOT_EXIST));

        user.setRole(role);
        user = userRepository.save(user);

        return userMapper.toUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAll() {
        var list = userRepository.findAll();
        return list.stream().map(userMapper::toUserResponseDto).toList();
    }

    @Override
    public List<UserResponseDto> getByRole(String name) {
        Role role = roleRepository.findByRoleName(name);

        if(Objects.isNull(role)){
            throw new GlobalException(ErrorCode.ROLE_NOT_EXIST);
        }

        var list = userRepository.findByRole(role);
        return list.stream().map(userMapper::toUserResponseDto).toList();
    }
}
