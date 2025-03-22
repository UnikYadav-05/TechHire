package com.example.TechHire.service;

import com.example.TechHire.entity.AddMember;
import com.example.TechHire.repository.AddMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddMemberService {

    @Autowired
    private AddMemberRepository memberRepository;

    @Autowired
    private NotificationService notificationService;

    // Add a new member
    public AddMember addMember(AddMember member) {
        AddMember savedMember = memberRepository.save(member);

        // Send notification to the new member
        notificationService.sendNotification(
                savedMember.getId(),
                "Welcome " + savedMember.getName() + "! You have been added as a " + savedMember.getDesignation() + "."
        );

        return savedMember;
    }

    // Get all members
    public List<AddMember> getAllMembers() {
        return memberRepository.findAll();
    }

    // Get member by ID
    public AddMember getMemberById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    // Get members by designation
    public List<AddMember> getMembersByDesignation(String designation) {
        return memberRepository.findByDesignation(designation);
    }

    // Update member details
    public AddMember updateMember(String id, AddMember updatedMember) {
        AddMember existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        existingMember.setName(updatedMember.getName());
        existingMember.setEmail(updatedMember.getEmail());
        existingMember.setDesignation(updatedMember.getDesignation());
        existingMember.setMobileNo(updatedMember.getMobileNo());

        AddMember updated = memberRepository.save(existingMember);

        // Send notification for update
        notificationService.sendNotification(
                updated.getId(),
                "Your profile details have been updated."
        );

        return updated;
    }


    // Delete a member
    public void deleteMember(String id) {
        AddMember member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        memberRepository.deleteById(id);

        // Send notification for deletion
        notificationService.sendNotification(
                id,
                "Your profile has been removed from the system."
        );
    }
}
