package com.cognizant.course.services;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.course.domain.Participant;
import com.cognizant.course.reader.XLSReader;
import com.cognizant.course.reader.XLSXReader;
import com.cognizant.course.repositories.ParticipantRepository;

@Service
public class ParticipantServiceImpl implements ParticipantService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ParticipantRepository participantRepository;

	@Override
	public boolean bulkUploadParticipant(Path path) {

		List<List<String>> list = null;
		try {
			if (("xls").equals(getFileExtension(path.toString()))) {
				list = new XLSReader().read(path.toString());
				
			} else if (("xlsx").equals(getFileExtension(path.toString()))) {
				list = new XLSXReader().read(path.toString());
				
			} else {
				System.out.println("Error parsing excel file plz upload valid file.");
			}
			List<Participant> participants = buildParticipantList(list);
			participants.stream().forEach(participantRepository::save);
			logger.debug("saveProduct called" + list.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	private List<Participant> buildParticipantList(
			List<List<String>> participantsRawStrLst) {

		List<Participant> participants = new ArrayList<Participant>();
		if(participantsRawStrLst == null)
			return participants;

		for (int i = 1; i < participantsRawStrLst.size(); i++) {
			try {
				Participant participant = new Participant();
				participant.setParticipant_id(participantsRawStrLst.get(i).get(
						1));
				participant.setParticipant_name(participantsRawStrLst.get(i)
						.get(2));
				participant.setDate_of_birth(participantsRawStrLst.get(i)
						.get(3));
				participant.setUser_name(participantsRawStrLst.get(i).get(4));
				participant.setPassword(participantsRawStrLst.get(i).get(5));
				participant.setEmail(participantsRawStrLst.get(i).get(6));
				participant.setMobile_no(participantsRawStrLst.get(i).get(7));
				participant.setAddress(participantsRawStrLst.get(i).get(8));
				participants.add(participant);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return participants;
	}
}