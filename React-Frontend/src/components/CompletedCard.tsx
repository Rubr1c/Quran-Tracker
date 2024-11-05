import axios from 'axios';
import { useState } from 'react';

type CardProps = {
  surahName: string;
  verseNumber: number;
  completedNumber: number;
  onUpdate: () => void;
};

export default function Card(props: CardProps) {
  const [completed, setCompleted] = useState<number>(props.completedNumber);
  const progressPercentage = (completed / props.verseNumber) * 100;
  


  const handleDirectUpdate = async (value: number) => {
    if (value < 0 || value > props.verseNumber) {
      alert(`Please enter a number between 0 and ${props.verseNumber}`);
      setCompleted(props.completedNumber);
      return;
    }

    try {
      await axios.put(
        'http://localhost:8080/api/tracking/update-surah-progress',
        null,
        {
          params: {
            surah: props.surahName,
            versesCompleted: value,
          },
          withCredentials: true,
        }
      );
      setCompleted(value);
      props.onUpdate();

    } catch (error) {
      console.log('Error updating surah progress:', error);
    }
  };

  const handleIncrement = async () => {
    try {
      await axios.put(
        'http://localhost:8080/api/tracking/increment-surah-progress',
        null,
        {
          params: {
            surah: props.surahName,
          },
          withCredentials: true,
        }
      );
      setCompleted((prev) => Math.min(props.verseNumber, prev + 1));
      props.onUpdate();
    } catch (error) {
      console.log('Error incrementing surah progress:', error);
    }
  };

  const handleDecrement = async () => {
    try {
      await axios.put(
        'http://localhost:8080/api/tracking/decrement-surah-progress',
        null,
        {
          params: {
            surah: props.surahName,
          },
          withCredentials: true,
        }
      );
      setCompleted((prev) => Math.max(0, prev - 1));
      props.onUpdate();
    } catch (error) {
      console.log('Error decrementing surah progress:', error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = Number(e.target.value);
    if (value < 0 || value > props.verseNumber) {
      alert(`Please enter a number between 0 and ${props.verseNumber}`);
      setCompleted(props.completedNumber);
    } else {
      setCompleted(value);
    }
  };

  const handleBlur = () => {
    setCompleted((prev) => Number(prev));
    handleDirectUpdate(completed);
  };

  return (
    <div
      className={`flex flex-col m-4 border-black border-2 p-2 ${
        completed === props.verseNumber ? 'opacity-50' : 'opacity-100'
      }`}
    >
      <div className="flex items-center">
        <p className="w-48">{props.surahName.replace('_', ' ')}</p>
        <div className="flex items-center ml-4">
          <button onClick={handleDecrement} className="px-2">
            ➖
          </button>
          <input
            type="number"
            value={completed}
            onChange={handleChange}
            onBlur={handleBlur}
            className="m-1 text-center appearance-none w-12 px-0"
            min={0}
            max={props.verseNumber}
          />
          <button onClick={handleIncrement} className="px-2">
            ➕
          </button>
        </div>
        <p className="ml-auto">{props.verseNumber}</p>
      </div>

      <div className="relative w-full h-4 bg-gray-200 mt-2">
        <div
          className="absolute top-0 left-0 h-full bg-blue-500"
          style={{ width: `${progressPercentage}%` }}
        />
      </div>
      <p>{`${progressPercentage.toFixed(2)}%`}</p>
    </div>
  );
}
