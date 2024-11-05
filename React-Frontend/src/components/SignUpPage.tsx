import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function SignupPage() {
  const navigate = useNavigate();
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  async function signupUser(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    e.preventDefault();
    try {
      const res = await axios.post(
        'http://localhost:8080/api/user/signup',
        { username: username, password: password },
        { withCredentials: true }
      );
      console.log(res.data);
      navigate('/');
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await axios.get(
          'http://localhost:8080/api/user/getUsername',
          {
            withCredentials: true,
          }
        );
        if (res.data) {
          navigate('/');
        }
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, []);

  return (
    <div className="flex items-center justify-center h-screen">
      <div className="bg-blue-100 flex flex-col p-6 justify-center items-center rounded-md">
        <h1 className="text-3xl font-bold mb-4 text-center">Signup</h1>
        <form className="flex flex-col items-center">
          <div className="mt-5 flex items-center">
            <label htmlFor="username">Username:</label>
            <input
              type="text"
              id="username"
              className="border-2 border-solid border-black rounded-md ml-3"
              onChange={(e) => setUsername(e.target.value)}
              value={username}
            />
          </div>
          <div className="mt-5 flex items-center">
            <label htmlFor="password">Password:</label>
            <input
              type="text"
              id="password"
              className="border-2 border-solid border-black rounded-md ml-3"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
            />
          </div>
          <div className="mt-5">
            <button
              type="submit"
              className="bg-green-400 px-4 py-1 rounded-md shadow-lg hover:bg-green-500 transition-colors ease-in-out"
              onClick={(e) => signupUser(e)}
            >
              Signup
            </button>
          </div>
        </form>
        <a href="/login" className="text-blue-500 underline text-sm mt-2">Login</a>
      </div>
    </div>
  );
}

export default SignupPage;
