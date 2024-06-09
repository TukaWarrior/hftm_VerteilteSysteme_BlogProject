package ch.hftm.blogproject.boundary;

import java.net.URI;
import java.util.Optional;

import ch.hftm.blogproject.control.BlogService;
import ch.hftm.blogproject.entity.Blog;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;

// This class provides the REST API endpoints for managing blog posts.

@Path("blogs")
public class BlogRessource {

    @Inject
    BlogService blogService;
    

    @GET
    public Response getBlogs(@QueryParam("searchString") Optional<String> searchString, @QueryParam("page") Optional<Long> pageIndex) {
        // Log.info("Search for: " + serachString);
        return Response.status(Status.OK).entity(blogService.getBlogs(searchString, pageIndex)).build();
    }

    @GET
    @Path("{id}")
    public Response getBlog (long id) {
        return Response.status(Status.OK).entity(blogService.getBlogById(id)).build();
    }

    @POST
    public Response addBlog(Blog blog, @Context UriInfo uriInfo) {
        Blog responseValue = blogService.addBlog(blog);
        URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(blog.getId())).build();
        if (responseValue != null) {
            return Response.created(uri).entity(responseValue).build();
        } else {
            return Response.created(uri).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteBlog(@PathParam("id") long id) {
        Blog responseValue = blogService.deleteBlog(id);
        if (responseValue != null) {
            Log.error("Blog with id " + id + " deleted successfully");
            return Response.status(Status.OK).entity(responseValue).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Path("{id}")
    public Response putBlog(@PathParam("id") long id, Blog blog) {
        Blog responseValue = blogService.replaceBlog(id, blog);
        if (responseValue != null) {
            return Response.status(Status.OK).entity(responseValue).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @PATCH
    @Path("{id}")
    public Response patchBlog(@PathParam("id") long id, Blog blog) {
        Blog responseValue = blogService.updateBlog(id, blog);

        if (responseValue != null) {
            return Response.status(Status.OK).entity(responseValue).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
