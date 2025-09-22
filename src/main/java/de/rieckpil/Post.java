package de.rieckpil;

import java.util.Set;

public record Post(long id,
                   String title,
                   String content,
                   long authorId,
                   Set<String> tags,
                   long reactions) {
}
