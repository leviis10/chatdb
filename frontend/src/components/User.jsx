import axios, { AxiosError } from "axios";
import { Fragment, useState } from "react";

function User() {
  const [errorMessages, setErrorMessages] = useState([]);
  const [users, setUsers] = useState([]);
  const loggedInId = +localStorage.getItem("id");

  const handleGetAllUser = async function () {
    try {
      const token = localStorage.getItem("token");

      const { data } = await axios.get("/api/v1/users", {
        headers: { Authorization: `Bearer ${token}` },
      });

      setUsers(data);
      setErrorMessages([]);
    } catch (err) {
      if (err instanceof AxiosError) {
        setErrorMessages(err.response.data.errors);
        setUsers([]);
      }
    }
  };

  return (
    <>
      <h2>Get All User</h2>
      <button onClick={handleGetAllUser}>Get All Users</button>

      {users.length > 0 && (
        <ul>
          {users.map((user) => (
            <Fragment key={user.id}>
              <li
                style={{
                  fontWeight: loggedInId === user.id ? "bold" : "normal",
                }}
              >
                {" "}
                id: {user.id}
              </li>
              <li>Username: {user.username}</li>
              <li>
                roles:
                <ul>
                  {user.roles.map((role) => (
                    <li key={role}>{role}</li>
                  ))}
                </ul>
              </li>
              <li>Created At: {new Date(user.createdAt).toDateString()}</li>
              <li>Updated At: {new Date(user.updatedAt).toDateString()}</li>
              <br />
            </Fragment>
          ))}
        </ul>
      )}

      {errorMessages.length > 0 && (
        <ul>
          {errorMessages.map((err, i) => (
            <li key={i}>{err}</li>
          ))}
        </ul>
      )}
    </>
  );
}

export default User;
