package com.synrgybootcamp.project.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PocketRequest {
    String name;
    MultipartFile picture;
    Integer target;
    Boolean primary;
    Date date;
}
