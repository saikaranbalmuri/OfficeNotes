package com.officenotes.services;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.officenotes.entities.Device;
import com.officenotes.model.NoteRequest;
import com.officenotes.persistence.HibernateUtil;

public class AddDevice implements NoteService<Device> {

	public Device service(NoteRequest request) {
		Device device = new Device();

		if (request.getEntities().containsKey("ADDDEVICE")) {
			@SuppressWarnings("unchecked")
			Map<String, String> devicemap = (Map<String, String>) request.getEntities().get("ADDDEVICE");

			String deviceid = devicemap.get("device_id");
			Long userid = Long.parseLong(devicemap.get("userid"));
			String device_type = devicemap.get("device_type");

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session
					.createQuery("select device_id from Device where userid= :userid and device_type= :device_type");
			query.setParameter("userid", userid);
			query.setParameter("device_type", device_type);

			String deviceidDB = null;
			if (query.list().size() > 0) {
				deviceidDB = (String) query.list().get(0);
			}
			System.out.println(deviceidDB);

			if (deviceidDB == null) {
				device.setDevice_id(devicemap.get("device_id"));
				device.setUserid(Integer.parseInt(devicemap.get("userid")));
				device.setDevice_os(devicemap.get("device_os"));
				device.setDevice_type(devicemap.get("device_type"));
				device.setLat(Double.parseDouble(devicemap.get("lat")));
				device.setLon(Double.parseDouble(devicemap.get("lon")));

				session.save(device);
				System.out.println("data Inserted");

				session.getTransaction().commit();
			} else if (!deviceid.equals(deviceidDB)) {
				Query query1 = session.createQuery(
						"update Device set device_id= :deviceid  where userid= :userid and device_type= :device_type");
				query1.setParameter("deviceid", deviceid);
				query1.setParameter("userid", userid);
				query1.setParameter("device_type", "Receiver");

				int result = query1.executeUpdate();
				System.out.println("Rows affected: " + result);

			} else if (deviceidDB.equals(deviceidDB)) {
				System.out.println("The device id is already inserted" + deviceid);
			}
		}
		return device;
	}

}
