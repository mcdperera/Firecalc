import Head from './head'
import Link from 'next/link';

const Nav = (props) => (
  <nav>
    <ul>
      <li>
        <Link prefetch href="/">
          <a>Home</a>
        </Link>
      </li>
      <ul>
        {!props.token  ?
        <div className="navItems">
          <li>
            <Link href="/signin">
              <a>Sign In</a>
            </Link>
          </li>
          <li>
            <Link href="/signup">
              <a>Sign Up</a>
            </Link>
          </li>
        </div>
        :
          <React.Fragment>
          <li>
            <Link href="/feedback">
              <a>Feedback</a>
            </Link>
          </li>
          <li>
            <Link href="/user">
              <a>User</a>
            </Link>
          </li>
          <li>
            <span onClick={props.signOut}>
              <a>Logout</a>
            </span>
          </li>
          </React.Fragment>
        }
      </ul>
    </ul>

    <style jsx>{`
      :global(body) {
        margin: 0;
        font-family: -apple-system,BlinkMacSystemFont,Avenir Next,Avenir,Helvetica,sans-serif;
      }
      nav {
        text-align: center;
      }
      ul {
        display: flex;
        justify-content: space-between;
      }
      nav > ul {
        padding: 4px 16px;
      }
      li {
        display: flex;
        padding: 6px 8px;
      }
      span, a {
        color: #067df7;
        text-decoration: none;
        font-size: 13px;
        cursor: pointer;
      }
      .navItems {
        display: flex;
      }
      .navItems li {
        padding: 6px 8px;
      }
    `}</style>
  </nav>
)

export default Nav
