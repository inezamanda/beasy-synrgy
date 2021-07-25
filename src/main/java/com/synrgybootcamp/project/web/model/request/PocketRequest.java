package com.synrgybootcamp.project.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PocketRequest {
    String name;

    @Nullable
    MultipartFile picture;

    Integer target;

    Date dueDate;
}
