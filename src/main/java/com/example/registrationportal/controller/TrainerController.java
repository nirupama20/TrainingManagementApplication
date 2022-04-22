package com.example.registrationportal.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.registrationportal.entity.Task;
import com.example.registrationportal.entity.Trainee;
import com.example.registrationportal.entity.Trainer;
import com.example.registrationportal.repo.TaskRepo;
import com.example.registrationportal.repo.TraineeRepo;
import com.example.registrationportal.repo.TrainerRepo;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
	@Autowired
	private TrainerRepo trr;
	@Autowired
	private TraineeRepo traineeRepo;
	@Autowired
	private TaskRepo tr;
	@RequestMapping(path = {"","/home"})
	public String home(Model m)
	{
		m.addAttribute("trsave", false);
		m.addAttribute("trname", null);
		m.addAttribute("trexist", false);
		m.addAttribute("trpassword",false);
		return "/trainer/home";
	}
	@PostMapping("/saveTrainer")
	public String saveTrainer(@ModelAttribute Trainer trainer,Model m)
	{
		String p1=trainer.getPassword();
		String p2=trainer.getConformpassword();
		System.out.println(p1);
		System.out.println(p2);
		Trainer tr=trr.findByEmail(trainer.getEmail());
		if(tr==null)
		{
			if(p1.equals(p2))
			{
				trr.save(trainer);
				m.addAttribute("trsave", true);
				m.addAttribute("trname", trainer.getFirstName());
				return "/trainer/home";
			}
			else
			{
				m.addAttribute("trpassword", true);
				return "/trainer/home";
				
			}
		}
		else
		{
			m.addAttribute("trexist", true);
			return "/trainer/home";
		}
	}
	@RequestMapping("/login")
	public String login() {
		
		return "/trainer/login";
	}
	@GetMapping("/allTrainer")
	public String allTrainee(Model m)
	{
		List<Trainer> list=trr.findAll();
		m.addAttribute("trainer", list);
		return "/trainer/user";
	}
	@PostMapping("/doLogin")
	public String doLogin(@ModelAttribute Trainer trainer,Model m,HttpSession session)
	{

		Trainer tr=trr.findByEmailAndPassword(trainer.getEmail(), trainer.getPassword());
		
		if(tr!=null)
		{
			if(tr.getPassword().equals(trainer.getPassword()))
			m.addAttribute("trainer", tr);
			List<Trainee> list=traineeRepo.findAll();
			m.addAttribute("trainee", list);
			session.setAttribute("curTrainer", tr);
			return "/trainer/dashboard1";
		}
		else
		{
			m.addAttribute("trsuccess", true);
			return "/trainer/login";
		}
		
	}
	
	
	
	
	
	
	@RequestMapping("/dashboard1")
	public String dashboard1() {
		return "trainer/dashboard1";
	}
	
	@GetMapping("/dashboard2")
	public String dashboard2(Model m,HttpSession session)
	{
		Trainer t=(Trainer)session.getAttribute("curTrainer");
		m.addAttribute("trainer", t);
		System.out.println(t);
		return "/trainer/dashboard2";
	}
	
	@PostMapping("/tupdate/{tid}")
	public String  tupdate(@PathVariable String tid,Model m,HttpSession session)
	{

		Trainer t=(Trainer)session.getAttribute("curTrainer");
		Integer id=Integer.parseInt(tid);
	    t= trr.findById(id).get();
		m.addAttribute("trainer", t);
		return "/trainer/trainerupdateform";
	
	}
	
	@PostMapping("updateTrainer/{id}")
	public String updateTrainer(@ModelAttribute Trainer trainer,@PathVariable("id")String tid,Model m)
	{
		System.out.println(tid);
		Integer id=Integer.parseInt(tid);
		Trainer t= trr.findById(id).get();
		t.setFirstName(trainer.getFirstName());
		t.setEmail(trainer.getEmail());
		t.setPhoneno(trainer.getPhoneno());
		trr.save(t);
		m.addAttribute("trainer", t);
		return "/trainer/dashboard2";
	}
	
	
	
	
	@RequestMapping("/logout")
	public String logout(Model m)
	{

		m.addAttribute("logout", true);
		return "/trainer/login";
	
	}
	@GetMapping("/seeDashboard")
	public String dashboard(Model m)
	{
		List<Trainee> list=traineeRepo.findAll();
		m.addAttribute("trainee", list);
		return "/trainer/dashboard";
	}
	@PostMapping("/update/{tid}")
	public String update(@PathVariable("tid")String tid,Model m)
	{
		Integer id=Integer.parseInt(tid);
		Trainee t= traineeRepo.findById(id).get();
		m.addAttribute("traineeUpdate", t);
		return "/trainer/updateForm";
	}
	@PostMapping("/delete/{tid}")
	public String delete(@PathVariable("tid")String tid,Model m)
	{
		Integer id=Integer.parseInt(tid);
		Trainee t= traineeRepo.findById(id).get();
		traineeRepo.delete(t);
		List<Trainee> list=traineeRepo.findAll();
		m.addAttribute("trainee", list);
		return "redirect:/trainer/seeDashboard";
	}
	@PostMapping("updateTrainee/{tid}")
	public String updateTrainee(@ModelAttribute Trainee trainee,@PathVariable("tid")String tid,Model m)
	{
		Integer id=Integer.parseInt(tid);
		Trainee t= traineeRepo.findById(id).get();
		t.setTname(trainee.getTname());
		t.setTemail(trainee.getTemail());
		traineeRepo.save(t);
		return "redirect:/trainer/seeDashboard";
	}
	@RequestMapping("/addTask")
	public String addTask(Model m)
	{
		m.addAttribute("saveTask", false);
		return "/trainer/taskForm";
	}
	@PostMapping("/saveTask")
	public String savetask(@ModelAttribute Task task,@RequestParam("email")String temail,Model m)
	{
		Trainee t=traineeRepo.findByTemail(temail);
		task.setTrainee(t);		
		List<Task> tasks= t.getTasks();
		tasks.add(task);
		t.setTasks(tasks);
		traineeRepo.save(t);
		m.addAttribute("saveTask", true);
		return "/trainer/taskForm";		
	}
	@GetMapping("/allTask")
	public String allTask(Model m)
	{
		List<Task> list= tr.findAll();
		m.addAttribute("task", list);
		return "/trainer/task";
	}
	@PostMapping("/deleteTask/{taskId}")
	public String deleteTask(@PathVariable("taskId")String tid,Model m)
	{

		Integer id=Integer.parseInt(tid);
		Task t=tr.findById(id).get();
		tr.delete(t);
		List<Task> list=tr.findAll();
		m.addAttribute("task", list);
		return "redirect:/trainer/allTask";
	
	}
	
	
	@PostMapping("/updateTask/{taskId}")
	public String updateTask(@PathVariable("taskId")String tid,Model m)
	{
		Integer id=Integer.parseInt(tid);
		Task t= tr.findById(id).get();
		m.addAttribute("taskUpdate", t);
		return "/trainer/taskUpdateForm";
	}
	
	
	@PostMapping("/updateTaskAction/{taskId}")
	public String updateTaskAction(@ModelAttribute Task task,@PathVariable String taskId,Model m)
	{

		Integer id=Integer.parseInt(taskId);
		Task t= tr.findById(id).get();
		t.setDescription(task.getDescription());
		tr.save(t);
		return "redirect:/trainer/allTask";
	
	}
}
