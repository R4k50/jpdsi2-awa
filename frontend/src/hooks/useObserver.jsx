import { useEffect, useRef, useState } from 'react';

export default function useObserver(options) {
  const [isVisible, setIsVisible] = useState(false);
  const ref = useRef(null);

  useEffect(()=> {
    const target = ref.current;

    const observer = new IntersectionObserver(
      entries => setIsVisible(entries[0].isIntersecting),
      options
    );

    if (target)
      observer.observe(target);

    return () => {
      if (target)
        observer.unobserve(target);
    }
  }, [ref, options]);

  return { ref, isVisible };
}
