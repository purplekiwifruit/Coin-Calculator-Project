import { useState } from "react";
import axios from "axios";

interface CoinChange {
  targetAmount: number;
  coinDenominators: number[];
}

const ALLOWED_DENOMINATIONS = [
  0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000,
];

function App() {
  const [targetAmount, setTargetAmount] = useState("");
  const [selectedDenominators, setSelectedDenominators] = useState<number[]>(
    [],
  );
  const [result, setResult] = useState<number[]>([]);
  const [error, setError] = useState("");

  const handleCheckboxChange = (denominator: number) => {
    setSelectedDenominators((prevState) =>
      prevState.includes(denominator)
        ? prevState.filter((d) => d !== denominator)
        : [...prevState, denominator],
    );
  };

  const handleReset = () => {
    setSelectedDenominators([]); // Clears all selected denominators
    setTargetAmount(""); // Resets the target amount
    setResult([]); // Clears the result array
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const coinChange: CoinChange = {
      targetAmount: parseFloat(targetAmount),
      coinDenominators: selectedDenominators,
    };

    // Reset previous errors and results
    setError("");
    setResult([]);

    // Validation for zero or invalid target amount
    if (
      !coinChange.targetAmount ||
      coinChange.targetAmount <= 0 ||
      coinChange.targetAmount > 10000
    ) {
      setError(
        "Please enter a valid target amount greater than 0 and less than 10,000.",
      );
      return; // Stop the function if validation fails
    }

    // Validation for empty denominators
    if (coinChange.coinDenominators.length === 0) {
      setError("Please select at least one coin denominator.");
      return; // Stop the function if validation fails
    }

    try {
      const response = await axios.post<number[]>(
        `${import.meta.env.VITE_API_URL}/coin`,
        coinChange,
        {
          headers: {
            "Content-Type": "application/json",
          },
        },
      );
      setResult(response.data);
    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        const errorMessage = error.response.data;
        setError(`Failed to calculate. Error: ${errorMessage}.`);
      } else {
        setError("Failed to calculate. Please try again.");
      }
    }
  };
  return (
    <div className="max-w-sm mx-auto p-4">
      <h1 className="text-3xl font-serif text-center mb-6 mt-4">
        Coin Change Calculator
      </h1>
      <form onSubmit={handleSubmit}>
        <div className="mb-6">
          <label className="block mb-2 text-lg font-medium text-gray-900 dark:text-white">
            Target Amount:
          </label>
          <input
            type="number"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            step="0.01"
            placeholder="0"
            value={targetAmount}
            onChange={(e) => setTargetAmount(e.target.value)}
            required
          />
          <p className="mt-2">Please enter a number between 0 and 10,000.00</p>
        </div>

        <div className="mb-6">
          <label className="block mb-2 text-lg font-medium text-gray-900 dark:text-white">
            Select Coin Denominators
          </label>
          <div className="flex flex-wrap align-middle">
            {ALLOWED_DENOMINATIONS.map((denominator) => (
              <div
                className="flex items-center mb-4 w-1/4 p-2"
                key={denominator}
              >
                <input
                  className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 rounded dark:bg-gray-700 dark:border-gray-600"
                  type="checkbox"
                  value={denominator}
                  checked={selectedDenominators.includes(denominator)}
                  onChange={() => handleCheckboxChange(denominator)}
                />
                <label className="ml-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                  {denominator}
                </label>
              </div>
            ))}
          </div>
        </div>
        <div className="flex justify-center space-x-2">
          <button
            type="submit"
            className="focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 w-32 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
          >
            Calculate
          </button>
          <button
            type="reset"
            onClick={handleReset}
            className="focus:outline-none text-white bg-yellow-400 hover:bg-yellow-500 focus:ring-4 focus:ring-yellow-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 w-32 dark:focus:ring-yellow-900"
          >
            Reset
          </button>
        </div>
      </form>
      {error && <p className="mt-10 error text-red-800">{error}</p>}
      {result.length > 0 && (
        <div className="mt-10">
          <h2 className="text-xl font-semibold mb-4">Result:</h2>
          <div className="flex flex-wrap gap-2">
            {result.map((coin, index) => (
              <div
                key={index}
                className="text-center bg-gray-200 px-4 py-2 rounded-full flex-wrap"
              >
                {coin}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
