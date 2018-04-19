import React from 'react';
import Link from 'next/link';
import withLayout from './layout';
import axios from 'axios';


class App extends React.Component {

   state = {
        menus: []
    }

    componentDidMount() {
        axios.get('http://localhost:3001/api/category')
            .then((response) => {
                this.setState({
                    menus: response.data.menus
                })
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
    const { menus } = this.state;

        return (
            <div>
                <div className="hero">
                <h1 className="title">Welcome to Firecalc!</h1>

                <div className="row">
                    {menus.map((menu, i) => {

                        let url = menu.title.toLowerCase().replace(/\s/g, '');

                        return <Link href={`/${url}`}>
                            <a className="card">
                                <img src={`https://raw.githubusercontent.com/mcdperera/Firecalc/master/services/public/images/${menu.image}`} alt="Firecalc img" />
                                <h3>{menu.title}</h3>
                            </a>
                        </Link>
                    })}
                </div>
                </div>

                <style jsx>{`
                .hero {
                    width: 100%;
                    color: #333;
                }
                .title {
                    margin: 0;
                    width: 100%;
                    padding-top: 20px;
                    line-height: 1.15;
                    font-size: 48px;
                }
                .title, .description {
                    text-align: center;
                }
                .row {
                    width: 258px;
                    margin: 20px auto 40px;
                    display: flex;
                    flex-direction: column;
                    justify-content: space-around;
                }
                .row img {
                    width: 150px;
                }
                .card {
                    padding: 18px 18px 24px;
                    width: auto;
                    text-align: center;
                    text-decoration: none;
                    color: #434343;
                    border: 1px solid #9B9B9B;
                }
                .card:hover {
                    border-color: #067df7;
                }
                .card h3 {
                    margin: 0;
                    color: #067df7;
                    font-size: 18px;
                }
                .card p {
                    margin: 0;
                    padding: 12px 0 0;
                    font-size: 13px;
                    color: #333;
                }
                `}</style>
            </div>
        )
    }
}

export default withLayout(App);