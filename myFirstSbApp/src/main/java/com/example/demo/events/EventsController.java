package com.example.demo.events;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.demo.github.GithubClient;
import com.example.demo.github.RepositoryEvent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * EventsController
 */
@Controller
public class EventsController {

    private final GithubProjectRepository repository;
    private final GithubClient githubClient;

    public EventsController(GithubClient githubClient, GithubProjectRepository repository) {
		this.githubClient = githubClient;
		this.repository = repository;
	}

    @GetMapping("/events/{repoName}")
    @ResponseBody
    public RepositoryEvent[] fetchEvents(@PathVariable String repoName){
        GithubProject project = this.repository.findByRepoName(repoName);

        return this.githubClient.fetchEvents(project.getOrgName(), project.getRepoName()).getBody();
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("projects",this.repository.findAll());
        return "admin";
    }


    @GetMapping("/")
	public String dashboard(Model model) {
		List<DashboardEntry> entries = StreamSupport
				.stream(this.repository.findAll().spliterator(), true)
				.map(p -> new DashboardEntry(p, githubClient.fetchEventsList(p.getOrgName(), p.getRepoName())))
				.collect(Collectors.toList());
		model.addAttribute("entries", entries);
		return "dashboard";
	}
}