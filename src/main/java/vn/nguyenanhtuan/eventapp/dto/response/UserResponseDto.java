package vn.nguyenanhtuan.eventapp.dto.response;

import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    int id;
    String email;
    String password;
    String username;
    String phone;
    String facultyName;
    String facultyLogo;
    String facultyDescription;
}
