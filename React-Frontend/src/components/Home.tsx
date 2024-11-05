import axios from 'axios';
import { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import Card from './CompletedCard';

interface SurahProgress {
  verseCount: number;
  completedVerses: number;
}

interface Surahs {
  surahProgress: Record<string, SurahProgress>;
}

export default function Home() {
  const navigate = useNavigate();
  const [user, setUser] = useState<string>('');
  const [surahs, setSurahs] = useState<Surahs>({ surahProgress: {} });
  const [updateTrigger, setUpdateTrigger] = useState(false);

  const toggleUpdateTrigger = useCallback(() => {
    setUpdateTrigger((prev) => !prev);
  }, []);

  async function fetchUserSession() {
    try {
      const res = await axios.get(
        'http://localhost:8080/api/user/getUsername',
        {
          withCredentials: true,
        }
      );
      setUser(res.data);
    } catch (error) {
      navigate('/login');
    }
  }

  useEffect(() => {
    fetchUserSession();
    getSurahs();
  }, [updateTrigger]);

  async function getSurahs() {
    try {
      const res = await axios.get<Surahs>(
        'http://localhost:8080/api/tracking/get-all-progress',
        {
          withCredentials: true,
        }
      );
      setSurahs(res.data);
    } catch (error) {
      console.log(error);
    }
  }

  const totalVerses = Object.values(surahs.surahProgress).reduce(
    (total, { verseCount }) => total + verseCount,
    0
  );
  const totalCompleted = Object.values(surahs.surahProgress).reduce(
    (total, { completedVerses }) => total + completedVerses,
    0
  );
  const overallPercentage =
    totalVerses > 0 ? (totalCompleted / totalVerses) * 100 : 0;

  const handleLogout = async () => {
    await axios.delete('http://localhost:8080/api/user/logout', {
      withCredentials: true,
    });
    navigate('/login');
  };

  return (
    <div className="p-4">
      <div className="flex justify-between items-center mb-4">
        <h1>Hello {user}!</h1>
        <p className="text-lg font-bold">
          Overall Progress: {overallPercentage.toFixed(2)}%
        </p>
        <button
          onClick={handleLogout}
          className="bg-red-500 text-white p-2 rounded"
        >
          Logout
        </button>
      </div>
      {Object.entries(surahs.surahProgress).map(
        ([surahName, { verseCount, completedVerses }]) => (
          <Card
            key={surahName}
            surahName={surahName}
            verseNumber={verseCount}
            completedNumber={completedVerses}
            onUpdate={toggleUpdateTrigger}
          />
        )
      )}
    </div>
  );
}
