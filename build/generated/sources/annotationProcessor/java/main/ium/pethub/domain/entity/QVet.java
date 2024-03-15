package ium.pethub.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVet is a Querydsl query type for Vet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVet extends EntityPathBase<Vet> {

    private static final long serialVersionUID = -1205465479L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVet vet = new QVet("vet");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    public final StringPath career = createString("career");

    public final StringPath closeHour = createString("closeHour");

    public final ListPath<Comment, QComment> commentList = this.<Comment, QComment>createList("commentList", Comment.class, QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<Follow, QFollow> followers = this.<Follow, QFollow>createList("followers", Follow.class, QFollow.class, PathInits.DIRECT2);

    public final StringPath hosName = createString("hosName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath openHour = createString("openHour");

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final ListPath<Review, QReview> reviewList = this.<Review, QReview>createList("reviewList", Review.class, QReview.class, PathInits.DIRECT2);

    public final QUser user;

    public QVet(String variable) {
        this(Vet.class, forVariable(variable), INITS);
    }

    public QVet(Path<? extends Vet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVet(PathMetadata metadata, PathInits inits) {
        this(Vet.class, metadata, inits);
    }

    public QVet(Class<? extends Vet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

