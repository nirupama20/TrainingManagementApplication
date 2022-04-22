package com.example.registrationportal.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.registrationportal.entity.Task;
import com.example.registrationportal.entity.Trainee;
import com.example.registrationportal.entity.Trainer;
import com.example.registrationportal.repo.TaskRepo;
import com.example.registrationportal.repo.TraineeRepo;

@Controller
@RequestMapping("/trainee")
public class TraineeController {

	@Autowired
	private TraineeRepo tr;
	@Autowired
	private TaskRepo taskRepo;
	@RequestMapping(path={"","/home"})
	public String home(Model m)
	{
		m.addAttribute("tsave", false);
		m.addAttribute("tname", null);
		m.addAttribute("texist", false);
		m.addAttribute("tpass",false);
		return "/trainee/home";
	}
	@PostMapping("/saveTrainee")
	public String saveTrainee(@ModelAttribute Trainee trainee,Model m)
	{
		Trainee t=tr.findByTemail(trainee.getTemail());
		String p1=trainee.getTpassword();
		String p2=trainee.getTconformpassword();
		System.out.println(p1);
		System.out.println(p2);
		if(t==null)
		{
			if(p1.equals(p2))
			{
				tr.save(trainee);
				m.addAttribute("tsave", true);
				m.addAttribute("tname", trainee.getTname());
				return "/trainee/home";
			}
			else
			{
				m.addAttribute("tpass", true);
				return "/trainee/home";
				
			}
		}
		else
		{
			m.addAttribute("texist", true);
			return "/trainee/home";
		}
		
	}
	@RequestMapping("/login")
	public String login() {
		
		return "/trainee/login";
	}
	@GetMapping("/allTrainee")
	public String allTrainee(Model m)
	{
		List<Trainee> list=tr.findAll();
		m.addAttribute("trainee", list);
		return "/trainee/user";
	}
	@PostMapping("/doLogin")
	public String doLogin(@ModelAttribute Trainee trainee,Model m,HttpSession session)
	{

		Trainee t=tr.findByTemailAndTpassword(trainee.getTemail(), trainee.getTpassword());
		
		if(t!=null)
		{
			if(t.getTpassword().equals(trainee.getTpassword()))
			m.addAttribute("trainee", t);
			session.setAttribute("curTrainee", t);
			return "/trainee/dashboard1";
		}
		else
		{
			m.addAttribute("tsuccess", true);
			return "/trainee/login";
		}
	
	}
	@RequestMapping("/dashboard1")
	public String dash1()
	{
		return "/trainee/dashboard1";
	}
	
	
	
	@RequestMapping("/logout")
	public String logout(Model m)
	{

		m.addAttribute("tlogout", true);
		return "/trainee/login";
	
	}
	@PostMapping("/update/{tid}")
	public String  update(@PathVariable String tid,Model m)
	{

		Integer id=Integer.parseInt(tid);
		Trainee t= tr.findById(id).get();
		m.addAttribute("trainee", t);
		return "/trainee/updateForm";
	
	}
	@PostMapping("updateTrainee/{tid}")
	public String updateTrainee(@ModelAttribute Trainee trainee,@PathVariable("tid")String tid,Model m)
	{
		Integer id=Integer.parseInt(tid);
		Trainee t= tr.findById(id).get();
		t.setTname(trainee.getTname());
		t.setTemail(trainee.getTemail());
		t.setTphoneno(trainee.getTphoneno());
		tr.save(t);
		m.addAttribute("trainee", t);
		return "/trainee/dashboard";
	}
	@GetMapping("/seeDashboard")
	public String dashboard(Model m,HttpSession session)
	{
		Trainee t=(Trainee)session.getAttribute("curTrainee");
		m.addAttribute("trainee", t);
		return "/trainee/dashboard";
	}
	
	
	@RequestMapping("/seeTask/{tid}")
	public String seeTask(@PathVariable("tid")String tid,Model m)
	{
		Integer id=Integer.parseInt(tid);
		List<Task> tasks = taskRepo.findByTrainee(id);
		m.addAttribute("task", tasks);
		return "/trainee/task";
	}
	
	
	@PostMapping("/deleteTask/{tid}")
	public String deleteTask(@PathVariable("tid")String tid,Model m,HttpSession session)
	{

		Integer id=Integer.parseInt(tid);
		Task t=taskRepo.findById(id).get();
		taskRepo.delete(t);
		List<Task> list=taskRepo.findByTrainee(t.getTrainee().getTid());
		m.addAttribute("task", list);
		Trainee trr=(Trainee)session.getAttribute("curTrainee");
		return "redirect:/trainee/seeTask/"+trr.getTid();
	
	}
	
	
	@PostMapping("/updateTask/{taskId}")
	public String updateTask(@PathVariable("taskId")String tid,Model m)
	{
		Integer id=Integer.parseInt(tid);
		Task t= taskRepo.findById(id).get();
		m.addAttribute("taskUpdate", t);
		return "/trainee/updateTask";
	}
	
	
	@PostMapping("/updateTaskAction/{taskId}")
	public String updateTaskAction(@ModelAttribute Task task,@PathVariable String taskId,Model m,HttpSession session)
	{

		Integer id=Integer.parseInt(taskId);
		Task t= taskRepo.findById(id).get();
		t.setDescription(task.getDescription());
		taskRepo.save(t);
		Trainee trr=(Trainee)session.getAttribute("curTrainee");
		return "redirect:/trainee/seeTask/"+trr.getTid();
	
	}
}
