/**
 * 
 */
package com.musicacademy.osgi.eventpublisher;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author imashiuyanahewa
 *
 */
public class EventPublisherImpl implements IEventPublisher {
	ArrayList<EventModel> eventsList = new ArrayList<EventModel>();
	Scanner sc = new Scanner(System.in).useDelimiter("\\n");

	/**
	 * default constructor
	 */
	public EventPublisherImpl() {
	}
	
	@Override
	public void chooseAnOption(String consumer) {
		while(true) {
			if(consumer.equals("student") || consumer.equals("admin")) {
				System.out.println("\n Select an option(1-3) to continue:");
				System.out.println("1). View all musical events");
		        System.out.println("2). Filter events by event category");
		        System.out.println("3). Exit");
		        System.out.print(">> ");
		        
		        int option = sc.nextInt(); System.out.println("");
		        
				if (option == 3) {
					System.out.println("Exiting...\n");
					break;
				}
				
		        switch(option) {
		        case 1:
		        	this.displayAllEvents();
		        	break;
		        case 2:
		        	this.filterByEventCategory();
		        	break;
		        default:
		        	System.out.println("Invalid input.please try again.");
		        }
			}
			else {
				// options for event organizer
				System.out.println("\nDear Event organizer, \n Please select an option(1-5) to continue:");
				System.out.println("1). Add new musical event");
				System.out.println("2). View all musical events");
		        System.out.println("3). Filter events by event category");
		        System.out.println("4). Cancel an event");
		        System.out.println("5). Exit");
		        System.out.print(">> ");
		        
		        int option = sc.nextInt(); System.out.println("");
		        
				if (option == 5) {
					System.out.println("Exiting...\n");
					break;
				}
				
		        switch(option) {
		        case 1:
		        	this.addNewEvent();
		        	break;
		        case 2:
		        	this.displayAllEvents();
		        	break;
		        case 3:
		        	this.filterByEventCategory();
		        	break;
		        case 4: 
		        	this.cancelEvent();
		        	break;

		        default:
		        	System.out.println("Invalid input.please try again.");
		        }
				
			}
		}
	}
	
	@Override
	public void addNewEvent() {
		
		char isContinue = 'y';
		while(isContinue == 'y') {
			EventModel event = new EventModel();
			System.out.println("----------------Add new musical event----------------");
			System.out.print("Enter event name: ");
			event.setEventName(sc.next());
			
			System.out.println("Event categories: ");
			this.displayAllEventCategories();
			System.out.print("Choose an event category (enter category number): ");
			//validate category input
			try {
				int selectedCategory = Integer.parseInt(sc.next());
				if(selectedCategory >= 1 && selectedCategory <= 7) {
					event.setCategory(selectedCategory);
				}
				else {
					System.out.println("Invalid input! Please re-enter event details.\n");
					continue;
				}
			}catch(NumberFormatException e) {
				e.getMessage();
				System.out.println("Invalid input! Please re-enter event details.\n");
				continue;
			}
			
			System.out.print("Enter event date (DD/MM/YYY): ");
			event.setDate(sc.next());
			
			System.out.print("Enter event venue: ");
			event.setVenue(sc.next());
			
			System.out.print("Enter duration: ");
			event.setDuration(sc.next());
			
			// save new event details
			try {
				eventsList.add(event);
				System.out.println("'"+event.getEventName()+"' "+"event added successfully...");
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Error!. Failed to add the event");
			}
			
			// to terminate adding events
			System.out.println("Do you want to add another event? (Y/N) ");
			isContinue = (sc.next().charAt(0));
			if(isContinue == 'y' || isContinue == 'Y') {
				isContinue = 'y';
			}
			else if(isContinue == 'n' || isContinue == 'N') {
				System.out.println("");
				break;
			}
			else {
				while(true) {
					System.out.println("Invalid input!");
					System.out.println("( Please enter Y or N )");
					isContinue = (sc.next().charAt(0));
					if(isContinue == 'y' || isContinue == 'Y' || isContinue == 'n' || isContinue == 'N') {
						break;
					}
				}
			}
		}
	}
	@Override
	public void displayAllEvents() {
		if(eventsList.isEmpty() == true) {
			System.out.println("No events to show :(");
			return;
		}
		for(EventModel event: eventsList) {
			this.displayEventDetails(event);
		}
	}

	@Override
	public void cancelEvent() {
		if(eventsList.isEmpty() == true) {
			System.out.println("No events to cancel :(");
			return;
		}
		// display all scheduled events
		System.out.println("All scheduled events: ");
		int eventId = 0;
		for(EventModel event: eventsList) {
			System.out.println("\t("+(eventId+1) + "). "+event.getEventName());
			eventId++;
		}
		// ask to choose which event to be canceled
		System.out.print("Select which event you want to cancel(Enter event number): ");
		int selectedEvent = sc.nextInt();
		System.out.print("Are you sure you want to cancel this event? (Y/N)");
		String answer = sc.next();
		if(answer.charAt(0) == 'y' || answer.charAt(0) == 'Y') {
			try {
				eventsList.remove(selectedEvent-1);
				System.out.println("Event was cancelled...\n");
				System.out.println("");
				System.out.println("Remaining musical events: ");
				this.displayAllEvents();
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Error!. Failed to delete the event\n");
			}
		} else if(answer.charAt(0) == 'n' || answer.charAt(0) == 'N') {
			try {
				System.out.println("Event was not cancelled...\n");
				return;
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("invalid input!");
			return;
		}
		
	}
	
	@Override
	public void filterByEventCategory() {
		if(eventsList.isEmpty() == true) {
			System.out.println("No events to show :(");
			return;
		}
		int categoryNo;
		System.out.println("Event categories: ");
		this.displayAllEventCategories();
		System.out.print("Choose an event category (Enter category number):");
		categoryNo = sc.nextInt();
		System.out.println("");
		
		int count = 0;
		for(EventModel event: eventsList) {
			int i = 1;
			if(event.getCategory() == categoryNo) {
				System.out.println("search result "+ i + ": ");
				this.displayEventDetails(event);
				i++;
				count++;
			}
		}
		if(count == 0) {
			System.out.println("No events found under this category.");
		}
	}
	
	public void displayAllEventCategories() {
		System.out.println("\t(1)- Indoor concert");
		System.out.println("\t(2)- Outdoor concert");
		System.out.println("\t(3)- Music Festival");
		System.out.println("\t(4)- Opera");
		System.out.println("\t(5)- Ochestra");
		System.out.println("\t(6)- Jazz event");
		System.out.println("\t(7)- Music competition");
	}
	
	public void displayEventDetails(EventModel event) {
		System.out.println("Event Name: "+event.getEventName());
		System.out.println("Event type: "+event.getCategoryName());
		System.out.println("Date: "+event.getDate());
		System.out.println("Venue: "+event.getVenue());
		System.out.println("Duration: "+event.getDuration());
		System.out.println("-------------------------");
	}
}

	

