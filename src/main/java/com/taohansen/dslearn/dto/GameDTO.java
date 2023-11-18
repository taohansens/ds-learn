package com.taohansen.dslearn.dto;

import com.taohansen.dslearn.entities.Game;
import com.taohansen.dslearn.services.validation.YearValid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.BeanUtils;

@YearValid
public class GameDTO {
    private Long id;
    @Size(min = 3, max = 20, message = "O título deve ter entre 3 e 20 caracteres")
    @NotBlank(message = "Campo obrigatório")
    private String title;
    private Double score;
    private Integer year;
    private String genre;
    private String platforms;
    @URL(message = "Favor entrar com uma URL válida")
    private String imgUrl;
    @NotBlank(message = "Campo obrigatório")
    private String shortDescription;
    @NotBlank(message = "Campo obrigatório")
    private String longDescription;

    public GameDTO() {
    }

    public GameDTO(Game entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
