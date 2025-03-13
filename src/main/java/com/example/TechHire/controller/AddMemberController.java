package com.example.TechHire.controller;

import com.example.TechHire.entity.AddMember;
import com.example.TechHire.service.AddMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addMembers")
public class AddMemberController {

    @Autowired
    private AddMemberService memberService;

    // Add a new member
    @PostMapping
    public AddMember addMember(@RequestBody AddMember member) {
        return memberService.addMember(member);
    }

    // Get all members
    @GetMapping
    public List<AddMember> getAllMembers() {
        return memberService.getAllMembers();
    }

    // Get a member by ID
    @GetMapping("/{id}")
    public AddMember getMemberById(@PathVariable String id) {
        return memberService.getMemberById(id);
    }

    // Get members by designation
    @GetMapping("/designation/{designation}")
    public List<AddMember> getMembersByDesignation(@PathVariable String designation) {
        return memberService.getMembersByDesignation(designation);
    }

    // Update member details
    @PutMapping("/{id}")
    public AddMember updateMember(@PathVariable String id, @RequestBody AddMember updatedMember) {
        return memberService.updateMember(id, updatedMember);
    }

    // Delete a member
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable String id) {
        memberService.deleteMember(id);
    }
}
