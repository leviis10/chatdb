import axios, { AxiosError } from "axios";
import { useRef, useState } from "react";
import stompClient from "../utils/stompClient";

function Login() {
  const usernameInputRef = useRef(null);
  const passwordInputRef = useRef(null);
  const [username, setUsername] = useState("");
  const [errorMessages, setErrorMessages] = useState([]);

  const handleLogin = async function (e) {
    try {
      e.preventDefault();
      const username = usernameInputRef.current.value;

      const { data } = await axios.post("/api/v1/auth/login", {
        username,
        password: passwordInputRef.current.value,
      });

      localStorage.setItem("token", data.token);
      localStorage.setItem("id", data.id);

      setUsername(username);
      setErrorMessages([]);

      usernameInputRef.current.value = "";
      passwordInputRef.current.value = "";

      stompClient.subscribe(`/topic/${data.id}`, (message) => {
        const payload = JSON.parse(message.body);
        console.log(`${payload.senderId}: ${payload.content}`);
      });
    } catch (err) {
      if (err instanceof AxiosError) {
        setErrorMessages(err.response.data.errors);
        setUsername("");
      }
    }
  };

  return (
    <>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label htmlFor="username">username</label>{" "}
          <input
            type="text"
            id="username"
            ref={usernameInputRef}
            autoComplete="off"
          />
        </div>

        <div>
          <label htmlFor="password">password</label>{" "}
          <input
            type="password"
            id="password"
            ref={passwordInputRef}
            autoComplete="off"
          />
        </div>

        <div>
          <button type="submit">Login</button>
        </div>
      </form>

      {username && (
        <div>
          <p>Logged in as {username}</p>
        </div>
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

export default Login;
