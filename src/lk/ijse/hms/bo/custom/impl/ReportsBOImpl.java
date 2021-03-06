package lk.ijse.hms.bo.custom.impl;

import lk.ijse.hms.bo.custom.ReportsBO;
import lk.ijse.hms.dao.DAOFactory;
import lk.ijse.hms.dao.custom.ReserveDAO;
import lk.ijse.hms.dao.custom.RoomDAO;
import lk.ijse.hms.dao.custom.StudentDAO;
import lk.ijse.hms.dto.ReserveDTO;
import lk.ijse.hms.dto.StudentDTO;
import lk.ijse.hms.entity.Reserve;
import lk.ijse.hms.entity.Room;
import lk.ijse.hms.entity.Student;

import java.util.ArrayList;

public class ReportsBOImpl implements ReportsBO {
    //Dependency injection - property injection
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    private final ReserveDAO reserveDAO = (ReserveDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.RESERVE);
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public ArrayList<ReserveDTO> getReservation() {
        ArrayList<ReserveDTO> reservationList = new ArrayList<>();
        for (Reserve r : reserveDAO.getAll()) {
            reservationList.add(new ReserveDTO(r.getRes_id(),
                    r.getDate(), r.getStudent_id().getStudent_id(), r.getRoom_id().getRoom_id(), r.getStatus()));
        }
        return reservationList;
    }

    @Override
    public StudentDTO getStudent(String id) {
        StudentDTO studentDTO = null;
        for (Student s : studentDAO.search(id)) {
            studentDTO = new StudentDTO(s.getStudent_id(), s.getName(), s.getAddress(), s.getContact_no(), s.getDob(), s.getGender());
        }
        return studentDTO;
    }

    @Override
    public ArrayList<StudentDTO> getRooms(String id) {
        ArrayList<Student> studentList = new ArrayList<>();
        ArrayList<StudentDTO> list = new ArrayList<>();
        //get room as an object
        Room room = roomDAO.get(id);
        //get reservations for this room
        for (Reserve r : reserveDAO.getReservations(room)) {
            //get students
            studentList.add(studentDAO.get(r.getStudent_id().getStudent_id()));
        }

        for (Student s : studentList) {
            list.add(new StudentDTO(s.getStudent_id(), s.getName(), s.getAddress(), s.getContact_no(), s.getDob(), s.getGender()));
        }
        return list;
    }
}
