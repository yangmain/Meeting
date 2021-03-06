package com.dajiao.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dajiao.service.BookingRoomService;
import com.dajiao.model.Meeting;
import com.dajiao.model.MeetingRoom;
import com.dajiao.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

/**
 * Servlet implementation class BookingRoomServlet
 */
@WebServlet("/BookingRoomServlet")
public class BookingRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Timestamp start;
	private Timestamp end;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookingRoomServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String success = "";
		User user = (User) request.getSession().getAttribute("person");
		if (user != null) {

			String startdate = (String) request.getParameter("startdate");
			String starttime = (String) request.getParameter("starttime");
			String enddate = (String) request.getParameter("enddate");
			String endtime = (String) request.getParameter("endtime");
			String topic = (String) request.getParameter("topic");
			String meetingRoom = (String) request.getParameter("meetingRoom");

			if (startdate != null && starttime != null && enddate != null
					&& endtime != null) {
				System.out.println(startdate + starttime);
				System.out.println(enddate + endtime);
				start = Timestamp.valueOf(startdate + " " + starttime + ":00");
				end = Timestamp.valueOf(enddate + " " + endtime + ":00");

				request.setAttribute("roomList", BookingRoomService.getRoomList(start, end));

				/*
				 * List<MeetingRoom> list = new ArrayList<MeetingRoom>();
				 * MeetingRoom room = new MeetingRoom(); room.setId(123);
				 * room.setName("hahahahaha"); room.setNum(321);
				 * room.setTotal(21); room.setType("normal"); list.add(room);
				 * request.setAttribute("roomList", list);
				 */

			}

			if (topic != null && meetingRoom != null) {
				Meeting meeting = new Meeting();
				meeting.setTopic(topic);
				/*
				 * User user =
				 * (User)request.getSession().getAttribute("person"); if(user ==
				 * null){
				 * request.getRequestDispatcher("./meetingManager.jsp").forward
				 * (request, response); }
				 * 
				 * meeting.setbookpeople(user.getaccount());
				 */
				meeting.setbookpeople(user.getName());
				meeting.setStarttime(start);
				meeting.setEndtime(end);
				meeting.setRoomname(meetingRoom);
				if (BookingRoomService.bookingRoom(meeting) == true) {
					success = "1";
				}
				System.out.println(topic);
				System.out.println(meetingRoom);
			}
			
			request.getRequestDispatcher("./bookingRoom.jsp?success="+success).forward(request, response);
		}else{
			request.getRequestDispatcher("./meetingManager.jsp").forward(request, response);
		}

	}

}
