package test.effectivetest.models.dto.search;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskSortFields {
    String sortBy;
    boolean ascending;

    public enum SortField {
        TITLE("title"),
        CREATED("created"),
        PRIORITY("priority"),
        STATUS("status");

        private final String field;

        SortField(String field) {
            this.field = field;
        }

        public String getField() {
            return field;
        }
    }
}