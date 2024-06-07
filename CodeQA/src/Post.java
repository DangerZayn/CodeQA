import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Post {
    private static int nextPostID = 1;
    private final int postID;
    private final String postTitle;
    private final String postBody;
    private final String[] postTags;
    private final String[] postTypes = {"Very Difficult", "Difficult", "Easy"};
    private final String postType;
    private final String[] postEmergencies = {"Immediately Needed", "Highly Needed", "Ordinary"};
    private final String postEmergency;
    private final ArrayList<String> postComments = new ArrayList<>();
    private int tagCount = 2;
    private int bodyCount = 250;

    public Post(String postTitle, String postBody, String[] postTags, String postType, String postEmergency) {
        this.postID = nextPostID++;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.postTags = postTags;
        this.postType = postType;
        this.postEmergency = postEmergency;
    }

    // add post method with validations and the file writing
    public boolean addPost() throws IOException {
        boolean isValid = true;

        // calling validations
        if (!titleValidation(this.postTitle)) {
            System.out.println("Invalid Title: The title should be between 10 and 250 characters.");
            isValid = false;
        }

        if (!typeValidation(this.postType)) {
            System.out.println("Invalid Type: The type should be one of 'Very Difficult', 'Difficult', or 'Easy'.");
            isValid = false;
        }

        if (!bodyValidation(this.postBody)) {
            System.out.println("Invalid Body Content: The body should be at least " + this.bodyCount + " characters long.");
            isValid = false;
        }

        if (!tagsValidation(this.postTags)) {
            System.out.println("Invalid Tags: Each tag should be between 2 and 10 characters and in lowercase. The number of tags should be between " + this.tagCount + " and 5.");
            isValid = false;
        }

        if (!emergencyValidation(this.postEmergency)) {
            System.out.println("Invalid Emergency Value: The emergency value should be 'Immediately Needed', 'Highly Needed', or 'Ordinary' based on the post type.");
            isValid = false;
        }

        if (!isValid) {
            System.out.println("Post validation failed.");
            return false;
        } else {
            String tagsString = String.join("/", this.postTags);
            try (var writer = new BufferedWriter(new FileWriter("CodeQA.txt", true))) {
                writer.write(this.postID + " , " + this.postTitle + " , " + this.postBody + " , " + this.postType + " , " + this.postEmergency + " , " + tagsString + "\n");
            }
            return true;
        }
    }

    // add comment method with the validations and the file writing
    public boolean addComment(String comment) throws IOException {
        if (!commentSizeValidation(comment)) {
            System.out.println("Invalid comment: A comment should contain between 4 and 10 words.");
            return false;
        }

        if ((isEasyOrOrdinary() && postComments.size() < 3) || (!isEasyOrOrdinary() && postComments.size() < 5)) {
            postComments.add(comment);
            try (var writer = new BufferedWriter(new FileWriter("CodeQA.txt", true))) {
                writer.write("Comment on Post " + this.postID + ": " + comment + "\n");
            }
            return true;
        } else {
            System.out.println("Maximum comments amount reached: An 'Easy' or 'Ordinary' post can have a maximum of 3 comments. Other types can have a maximum of 5 comments.");
            return false;
        }
    }

    // comment word count validation
    private boolean commentSizeValidation(String comment) {
        String[] words = comment.trim().split("\\s+");
        int wordCount = words.length;
        return 4 <= wordCount && wordCount <= 10;
    }

    // type check for easy and ordinary
    private boolean isEasyOrOrdinary() {
        return Objects.equals(this.postType, "Easy") || Objects.equals(this.postEmergency, "Ordinary");
    }

    // title validation
    private boolean titleValidation(String title) {
        return 10 <= title.length() && title.length() <= 250;
    }

    // body validation
    private boolean bodyValidation(String body) {
        return body.length() >= this.bodyCount;
    }

    // tags validation
    private boolean tagsValidation(String[] tags) {
        if (tags.length < this.tagCount || tags.length >= 5) {
            return false;
        }
        for (String tag : tags) {
            if (tag.length() <= 2 || tag.length() >= 10 || !tag.equals(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    // type validation this will set the tag count and body count values depends on the type
    private boolean typeValidation(String type) {
        if (Objects.equals(type, "Easy")) {
            this.tagCount = 3;
        } else if (Objects.equals(type, "Very Difficult") || Objects.equals(type, "Difficult")) {
            this.bodyCount = 300;
        } else {
            return false;
        }
        return true;
    }

    // emergency validation
    private boolean emergencyValidation(String emergency) {
        if (Objects.equals(this.postType, "Easy")) {
            return Objects.equals(emergency, "Ordinary");
        } else if (Objects.equals(this.postType, "Very Difficult") || Objects.equals(this.postType, "Difficult")) {
            return Objects.equals(emergency, "Immediately Needed") || Objects.equals(emergency, "Highly Needed");
        } else {
            return false;
        }
    }
}
