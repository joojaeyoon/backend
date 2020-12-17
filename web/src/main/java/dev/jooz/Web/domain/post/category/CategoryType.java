package dev.jooz.Web.domain.post.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {
    CLOTH("옷"),
    ETC("기타");

    private String category;
}
