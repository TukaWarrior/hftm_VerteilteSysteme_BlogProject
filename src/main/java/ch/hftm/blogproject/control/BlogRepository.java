package ch.hftm.blogproject.control;

import ch.hftm.blogproject.entity.Blog;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog>{

    private List<Blog> blogs = new ArrayList<>();

    public BlogRepository() {
        blogs.add(new Blog("First Blog", "This is my first blog"));
        blogs.add(new Blog("Second Blog", "This is my second blog"));
    }

    public List <Blog> getBlogs() {
        return blogs;
    }

    public void addBlog(Blog blog) {
        blogs.add(blog);
    }
}