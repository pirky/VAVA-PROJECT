package project.model.books;

import javafx.scene.image.ImageView;
import project.model.CustomImage;

public class TableBook extends Book{
    private ImageView imageView = new ImageView();

    public TableBook(int id, String title, String author, String note, CustomImage image) {
        super(id, title, author, note, image);
        this.imageView.setImage(image.getImage());
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitHeight(150);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Object clone(){
        return new TableBook(this.getId(), this.getTitle(), this.getAuthor(), this.getNote(), this.getImage());
    }
}
