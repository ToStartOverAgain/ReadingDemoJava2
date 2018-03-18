/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingnews;

/**
 *
 * @author daolinh
 */
public class News {

    private long id;
    private String title;
    private String description;
    private String content;
    private String publicTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void printNews(){
        System.out.println("Public time: " + this.publicTime);
        System.out.println("========================");
        System.out.println("Title: " + this.title);
        System.out.println("========================");
        System.out.println("Description: " + this.description);
        System.out.println("========================");
        System.out.println("Content: " + this.content);        
    }

}
