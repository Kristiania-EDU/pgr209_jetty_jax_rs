import {useEffect, useState} from 'react'
import './App.css'

function App() {
  return (
    <div className="App">
        <h1>Listen med bøker: </h1>
        <BookList/>
    </div>
  )
}

function BookList() {
    const [loading, setLoading] = useState(true);
    const [books, setBooks] = useState([]);

    console.log(books);

    useEffect(() => {
        (async () => {
            const res = await fetch('/api/books');
            const booksResult = await res.json();

            console.log('Hello Books: ', booksResult);

            setBooks(booksResult);
            setLoading(false);
        }) ();
    }, [ setBooks ]);


    if(loading) {
        return (
        <div>
            <span>Loading...</span>
        </div>);
    }

    return (
        <ul style={{listStyle: "none"}}>
            {books.map(x =>
                <li>
                    {x.title}
                </li>)}
        </ul>);
}

export default App;
