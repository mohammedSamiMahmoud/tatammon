package com.example.tatmon.Model;

public class Article {

    /**
     *  "id" => $row['id'],
     *                 "docName" => $row2['name'],
     *                 "header" => $row['header'],
     *                 "content" => $row['content'],
     *                 "image" => "https://smarttracks.org/test/tat/API/src/routes/Aimages/" . $row['image'],
     *                 "keyWords" => $row['keyWords']
     * */
    String id,docName,header,content,image,keyWords;

    public Article(String id, String docName, String header, String content, String image, String keyWords) {
        this.id = id;
        this.docName = docName;
        this.header = header;
        this.content = content;
        this.image = image;
        this.keyWords = keyWords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}
