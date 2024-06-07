import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class PostTest {
    private Post postEasy;
    private Post postDifficult;

    @BeforeEach
    void setUp() {
        String[] tags = {"java", "mern", "kotlin"};
        postEasy = new Post("Sample Title", "This is a sample body content that is long enough to pass the validation. It should have at least 250 characters, so I'm adding more text here to ensure it meets the requirement. Java programming is fun and interesting! Learning Java can open many opportunities in software development, and mastering it can be very rewarding.", tags, "Easy", "Ordinary");

        String[] difficultTags = {"tag1", "tag2", "tag3"};
        postDifficult = new Post("Difficult Title", "Difficult Body", difficultTags, "Difficult", "Highly Needed");
    }

    @Test
    public void Post_1() throws IOException {
        assertTrue(postEasy.addPost());
    }

    @Test
    void Post_2() throws IOException {
        Post post = new Post("Short", "Valid Body", new String[]{"tag1", "tag2"}, "Easy", "Ordinary");
        assertFalse(post.addPost());
    }

    @Test
    void Post_3() throws IOException {
        Post post = new Post("Valid Title", "Short Body", new String[]{"tag1", "tag2"}, "Easy", "Ordinary");
        assertFalse(post.addPost());
    }

    @Test
    void Post_4() throws IOException {
        Post post = new Post("Valid Title", "Valid Body", new String[]{"tag1"}, "Easy", "Ordinary");
        assertFalse(post.addPost());
    }

    @Test
    void Post_5() throws IOException {
        Post post = new Post("Valid Title", "Valid Body", new String[]{"tag1", "tag2"}, "Medium", "Ordinary");
        assertFalse(post.addPost());
    }

    @Test
    void Post_6() throws IOException {
        Post post = new Post("Valid Title", "Valid Body", new String[]{"tag1", "tag2"}, "Easy", "Highly Needed");
        assertFalse(post.addPost());
    }

    @Test
    void Post_7() throws IOException {
        Post post = new Post("Valid Title", "Valid Body", new String[]{"TagWithUpperCase"}, "Easy", "Ordinary");
        assertFalse(post.addPost());
    }

    @Test
    void Post_8() throws IOException {
        Post post = new Post("Valid Title", "Valid Body", new String[]{"sh"}, "Easy", "Ordinary");
        assertFalse(post.addPost());
    }

    @Test
    void Post_9() throws IOException {
        Post post = new Post("Valid Title", "Valid Body", new String[]{"thisisaverylongtag"}, "Easy", "Ordinary");
        assertFalse(post.addPost());
    }

    @Test
    void Comment_1() throws IOException {
        assertTrue(postEasy.addComment("This is a valid comment"));
    }

    @Test
    void Comment_2() throws IOException {
        assertFalse(postEasy.addComment("Too short"));
    }

    @Test
    void Comment_3() throws IOException {
        assertFalse(postEasy.addComment("This comment has way too many words to be valid for this test"));
    }

    @Test
    void Comment_4() throws IOException {
        postEasy.addComment("This is a valid comment one");
        postEasy.addComment("This is a valid comment two");
        postEasy.addComment("This is a valid comment three");
        assertFalse(postEasy.addComment("This should not be added"));
    }

    @Test
    void Comment_5() throws IOException {
        postDifficult.addComment("This is a valid comment one");
        postDifficult.addComment("This is a valid comment two");
        postDifficult.addComment("This is a valid comment three");
        postDifficult.addComment("This is a valid comment four");
        postDifficult.addComment("This is a valid comment five");
        assertFalse(postDifficult.addComment("This should not be added"));
    }

    @Test
    void Comment_6() throws IOException {
        assertTrue(postDifficult.addComment("This comment is valid and the post type allows it"));
    }
}