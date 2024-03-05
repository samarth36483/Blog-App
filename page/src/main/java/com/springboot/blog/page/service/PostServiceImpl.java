package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.PostDTO;
import com.springboot.blog.page.dto.PostResponse;
import com.springboot.blog.page.exception.ResourceNotFoundException;
import com.springboot.blog.page.model.Category;
import com.springboot.blog.page.model.Post;
import com.springboot.blog.page.repository.CategoryRepository;
import com.springboot.blog.page.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public Post createPost(PostDTO dto) {
        Category category = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());
        post.setCategory(category);
        return postRepository.save(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        // create an instance of pageable
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content from page object
        List<Post> list = posts.getContent();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(list);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public Post getPostById(long id) {
        Optional<Post> Optpost = postRepository.findById(id);
        if(Optpost.isEmpty())
            throw new ResourceNotFoundException("No resource found");

        Post post = Optpost.get();
        return post;
    }

    @Override
    public List<PostDTO> getPostByCategory(long category_id) {
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        List<Post> posts = postRepository.findByCategoryId(category_id);

        List<PostDTO> postDTOS = posts.stream().map(post -> mapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return postDTOS;
    }

    @Override
    public Post updatePost(PostDTO dto, long id) {
        Category category = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Post post = getPostById(id);
        if(dto.getTitle() != null){
            post.setTitle(dto.getTitle());
        }
        if(dto.getDescription() != null){
            post.setDescription(dto.getDescription());
        }
        if(dto.getContent() != null){
            post.setContent(dto.getContent());
        }
        post.setCategory(category);
        return postRepository.save(post);
    }

    @Override
    public void delete(long id) {
        postRepository.deleteById(id);
    }
}
